package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseListModel
import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.Backpack
import org.lanier.gameserve2.entity.dto.BackpackDto
import org.lanier.gameserve2.entity.dto.MarketDto
import org.lanier.gameserve2.service.BackpackService
import org.lanier.gameserve2.service.MarketService
import org.lanier.gameserve2.service.PetService
import org.lanier.gameserve2.utils.CommonUtil
import org.springframework.web.bind.annotation.*

/**
 * Created by 幻弦让叶
 * Date 2024/2/24 18:57
 */
@RestController
@RequestMapping("/market")
class MarketController(
    private val marketService: MarketService,
    private val backpackService: BackpackService,
    private val petService: PetService
) : BaseController() {

    @GetMapping("/seeds")
    fun getSeeds(
        @RequestParam("page") page: Int = 1,
        @RequestParam("pageSize") pageSize: Int = 10
    ): BaseModel<BaseListModel<MarketDto>> {
        val offset = handleOffset(page, pageSize)
        val total: Int = marketService.seedTotal()
        val hasNext = offset + pageSize < total
        return BaseModel.successList(hasNext, marketService.getSeeds(offset, pageSize))
    }

    @GetMapping("/fertilizer")
    fun getFertilizer(
        @RequestParam("page") page: Int = 1,
        @RequestParam("pageSize") pageSize: Int = 10
    ): BaseModel<BaseListModel<MarketDto>> {
        val offset = handleOffset(page, pageSize)
        val total: Int = marketService.fertilizerTotal()
        val hasNext = offset + pageSize < total
        return BaseModel.successList(hasNext, marketService.getFertilizer(offset, pageSize))
    }

    @PostMapping("/purchase")
    fun buyProduct(
        @RequestParam("petId") petId: String?,
        @RequestParam("userId") userId: Int,
        @RequestParam("type") type: Int,
        @RequestParam("realPropId") propId: Int,
        @RequestParam("quantity") quantity: Int,
        @RequestParam("marketItemId") itemId: Int
    ): BaseModel<Boolean> {
        if (petId.isNullOrEmpty()) {
            return BaseModel.notFoundUser()
        }
        val pid: Int = CommonUtil.toInteger(petId)
        if (pid < 0) {
            return BaseModel.actionFailed()
        }
        val items: List<MarketDto> = marketService.getItemById(itemId)
        if (items.size != 1) {
            return BaseModel.failedBool("商品暂不支持购买哦~")
        }
        val petCoin: Int = petService.getPetCoin(pid)
        val needCoin: Int = items[0].price!! * quantity
        if (needCoin > petCoin) {
            return BaseModel.failedBool("金币不足哦")
        }
        val backpackProp: BackpackDto? = backpackService.getPropByTypeId(pid, type, propId)
        val backpack: Backpack = Backpack(
            bpkId = backpackProp?.bpkId?:0,
            userId = userId,
            petId = pid,
            type = type,
            realPropId = propId,
        )
        val updatePropSuccess: Boolean
        if (backpackProp != null && backpackProp.amount > 0) {
            backpack.amount = (backpackProp.amount + quantity)
            updatePropSuccess = backpackService.updateProp(backpack)
        } else {
            backpack.amount = (quantity)
            updatePropSuccess = backpackService.addProp(backpack)
        }
        if (updatePropSuccess) {
            val consumeSuccess: Boolean = petService.consumeCoin(needCoin, pid)
            if (consumeSuccess) {
                return BaseModel.successBool("success")
            }
            return BaseModel.failedBool("购买失败了 1")
        }
        return BaseModel.failedBool("购买失败了 2")
    }
}
