package org.lanier.gameserve2.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.lanier.gameserve2.base.BaseListModel
import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.SpiritAction
import org.lanier.gameserve2.entity.dto.SpiritDto
import org.lanier.gameserve2.service.SpiritService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by 黄瓜
 * Date 2024/5/2 15:44
 */
@RestController
@RequestMapping("/spirit")
class SpiritController(
    val spiritService: SpiritService,
) : BaseController() {

    /**
     * 全部的精灵
     */
    @GetMapping("/all")
    fun obtainAllSpirits(
        @RequestParam("page") page: Int, // 从1开始
        @RequestParam("pageSize") pageSize: Int = 10,
    ) : BaseModel<BaseListModel<SpiritDto>> {
        val offset = handleOffset(page, pageSize)
        val mapper = ObjectMapper()
        val spirits = spiritService.obtainSpirit(offset, pageSize).map {
            val actions = mapper.readValue(it.actions, SpiritAction::class.java)
            SpiritDto(
                id = it.id,
                name = it.name,
                preview = it.preview,
                actions = actions
            )
        }
        val total = spiritService.spiritTotal()
        val hasNext = offset + pageSize < total
        return BaseModel.successList(hasNext, spirits)
    }

    /**
     * 随机精灵
     */
    @GetMapping("/random")
    fun randomSpirit() : BaseModel<SpiritDto> {
        val spirit = spiritService.randomSpirit()
        val mapper = ObjectMapper()
        val actions = mapper.readValue(spirit.actions, SpiritAction::class.java)
        val dto = SpiritDto(
            id = spirit.id,
            name = spirit.name,
            preview = spirit.preview,
            actions = actions
        )
        return BaseModel.success(dto)
    }
}