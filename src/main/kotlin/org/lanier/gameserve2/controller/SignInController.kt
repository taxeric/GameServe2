package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.dto.SignInConfigDTO
import org.lanier.gameserve2.service.PropService
import org.lanier.gameserve2.service.SignInConfigService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Calendar

/**
 * 签到
 */
@RestController
@RequestMapping("/sign")
class SignInController(
    private val propService: PropService,
    private val signInConfigService: SignInConfigService,
) {

    /**
     * 获取签到奖励
     */
    @GetMapping("/getRewards")
    fun getRewardsByYearAndMonth(
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Int,
    ) : BaseModel<List<SignInConfigDTO>> {
        val result = signInConfigService
            .getAllRewardsByYearAndMonth(year, month)
        return BaseModel.success(
            data = result
        )
    }

    /**
     * 新增签到奖励
     *
     * @param year 年份
     * @param month 月份
     * @param amount 数量
     * @param propId 道具id, 可空
     * @param propType 道具类型, 可空
     * @param category 类别, 默认为货币
     */
    @PostMapping("/addReward")
    fun addReward(
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Int,
        @RequestParam("amount") amount: Int,
        @RequestParam("propId") propId: Int? = -1,
        @RequestParam("propType") propType: Int? = -1,
        @RequestParam("category") category: Int? = -1,
        @RequestParam("remark") remark: String? = "签到奖励~~~"
    ) : BaseModel<Boolean> {
        val curYear = Calendar.getInstance().get(Calendar.YEAR)
        if (year < curYear) {
            return BaseModel.failureBoolean(message = "年份有误")
        }
        val curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        if (month < curMonth) {
            return BaseModel.failureBoolean(message = "月份有误")
        }
        if (category == null) {
            // TODO 添加货币操作
        } else {
            if (category == 1) {
                // TODO 添加货币操作
            } else if (category == 2) {
                if (propId == null || propType == null) {
                    return BaseModel.failureBoolean(message = "请确定道具类型~")
                }
                val bpid = propService.getProp(propType, propId) // 找到主键
                if (bpid < 0) {
                    return BaseModel.failureBoolean(message = "没有找到指定类型的道具~")
                }
                val success = signInConfigService.addReward(
                    category = 2,
                    amount = amount,
                    year = year,
                    month = month,
                    propId = propId,
                    propType = propType,
                    remark = remark?:"签到奖励~~~"
                )
                if (success) {
                    return BaseModel.success(data = true)
                }
                return BaseModel.failureBoolean(message = "设置失败 ,,Ծ‸Ծ,,")
            } else {
                return BaseModel.failureBoolean(message = "无法添加未知类型~")
            }
        }
        return BaseModel.success(data = true)
    }
}