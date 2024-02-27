package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.dto.BackpackDto
import org.lanier.gameserve2.service.BackpackService
import org.lanier.gameserve2.service.DrugService
import org.lanier.gameserve2.service.FoodService
import org.lanier.gameserve2.service.ToiletriesService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 17:26
 */
@RestController
@RequestMapping("/backpack")
class BackpackController(
    private val backpackService: BackpackService,
    private val foodService: FoodService,
    private val toiletriesService: ToiletriesService,
    private val drugService: DrugService,
) {

    @GetMapping("/foods")
    fun getBackpackFoodsInfo(
        @RequestParam("userId") userId: Int,
        @RequestParam("petId") petId: Int,
    ) : BaseModel<List<BackpackDto>> {
        val dto = foodService.getFoodsByUserAndPetId(userId, petId)
        return BaseModel.success(
            data = dto
        )
    }

    @GetMapping("/toiletries")
    fun getBackpackToiletriesInfo(
        @RequestParam("userId") userId: Int,
        @RequestParam("petId") petId: Int,
    ) : BaseModel<List<BackpackDto>>{
        val toiletries = toiletriesService.getToiletriesByUserAndPetId(userId, petId)
        return BaseModel.success(
            data = toiletries
        )
    }

    @GetMapping("/drugs")
    fun getBackpackDrugsInfo(
        @RequestParam("userId") userId: Int,
        @RequestParam("petId") petId: Int,
    ) : BaseModel<List<BackpackDto>>{
        val drugs = drugService.getDrugByUserAndPetId(userId, petId)
        return BaseModel.success(
            data = drugs
        )
    }
}