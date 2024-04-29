package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseListModel
import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.dto.BackpackDto
import org.lanier.gameserve2.service.BackpackService
import org.lanier.gameserve2.service.DrugService
import org.lanier.gameserve2.service.FoodService
import org.lanier.gameserve2.service.ToiletriesService
import org.lanier.gameserve2.utils.CommonUtil
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
) : BaseController() {

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

    @GetMapping("/seeds")
    fun getSeeds(
        @RequestParam("petId") petId: String?,
        @RequestParam("page") page: Int,
        @RequestParam("pageSize") pageSize: Int
    ): BaseModel<BaseListModel<BackpackDto>> {
        if (petId.isNullOrEmpty()) {
            return BaseModel.notFoundUser()
        }
        val pid: Int = CommonUtil.toInteger(petId)
        if (pid < 0) {
            return BaseModel.actionFailed()
        }
        val offset: Int = handleOffset(page, pageSize)
        val total = backpackService.getSeedTotal(pid)
        val hasNext = offset + pageSize < total
        return BaseModel.successList(hasNext, backpackService.getSeedsByPid(pid, offset, pageSize))
    }

    @GetMapping("/fertilizer")
    fun getFertilizer(
        @RequestParam("petId") petId: String?,
        @RequestParam("page") page: Int,
        @RequestParam("pageSize") pageSize: Int
    ): BaseModel<BaseListModel<BackpackDto>> {
        if (petId.isNullOrEmpty()) {
            return BaseModel.notFoundUser()
        }
        val pid: Int = CommonUtil.toInteger(petId)
        if (pid < 0) {
            return BaseModel.actionFailed()
        }
        val offset: Int = handleOffset(page, pageSize)
        val total = backpackService.getFertilizerTotal(pid)
        val hasNext = offset + pageSize < total
        return BaseModel.successList(hasNext, backpackService.getFertilizerByUid(pid, offset, pageSize))
    }

    @GetMapping("/crops")
    fun getCrops(
        @RequestParam("petId") petId: String?,
        @RequestParam("page") page: Int,
        @RequestParam("pageSize") pageSize: Int
    ): BaseModel<BaseListModel<BackpackDto>> {
        if (petId.isNullOrEmpty()) {
            return BaseModel.notFoundUser()
        }
        val pid: Int = CommonUtil.toInteger(petId)
        if (pid < 0) {
            return BaseModel.actionFailed()
        }
        val offset: Int = handleOffset(page, pageSize)
        val total = backpackService.getCropTotal(pid)
        val hasNext = offset + pageSize < total
        return BaseModel.successList(hasNext, backpackService.getCropsByUid(pid, offset, pageSize))
    }
}