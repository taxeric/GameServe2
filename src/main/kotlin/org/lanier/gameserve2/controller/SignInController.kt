package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseListModel
import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.SignInConfig
import org.lanier.gameserve2.entity.UserSignInLog
import org.lanier.gameserve2.entity.dto.SignInConfigDto
import org.lanier.gameserve2.service.PropService
import org.lanier.gameserve2.service.SignInConfigService
import org.lanier.gameserve2.service.UserSignInService
import org.lanier.gameserve2.utils.DateUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 签到
 */
@RestController
@RequestMapping("/sign-in")
class SignInController(
    private val propService: PropService,
    private val signInService: UserSignInService,
    private val signInConfigService: SignInConfigService,
) {

    /**
     * 获取签到奖励
     */
    @GetMapping("/getRewards")
    fun getRewardsByYearAndMonth(
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Int,
    ) : BaseModel<List<SignInConfigDto>> {
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
        @RequestParam("day") day: Int,
        @RequestParam("amount") amount: Int,
        @RequestParam("propId") propId: Int? = -1,
        @RequestParam("propType") propType: Int? = -1,
        @RequestParam("category") category: Int? = -1,
        @RequestParam("remark") remark: String? = "签到奖励~~~"
    ) : BaseModel<Boolean> {
        if (DateUtil.isCurrentYear(year).not()) {
            return BaseModel.failureBoolean(message = "年份有误哦~")
        }
        if (DateUtil.isCurrentMonth(month).not()) {
            return BaseModel.failureBoolean(message = "月份有误哦~")
        }
        if (category == null) {
            return addCurrencyToSignInConfig(amount, year, month, day, remark)
        } else {
            return when (category) {
                SignInConfig.CATEGORY_CURRENCY -> {
                    addCurrencyToSignInConfig(amount, year, month, day, remark)
                }

                SignInConfig.CATEGORY_PROP -> {
                    addPropToSignInConfig(propId, propType, amount, year, month, day, remark)
                }

                // TODO 无奖励的普通签到

                else -> {
                    BaseModel.failureBoolean(message = "无法添加未知类型~")
                }
            }
        }
    }

    @GetMapping("/get-mine-logs")
    fun getMineSignInLogs(
        @RequestParam("petId") petId: Int,
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Int,
        @RequestParam("page") page: Int, // 从1开始
        @RequestParam("pageSize") pageSize: Int? = 10,
    ) : BaseModel<BaseListModel<UserSignInLog>> {
        if (petId < 0) {
            return BaseModel.failureList(message = "没有找到id耶～")
        }
        if (DateUtil.isCurrentYear(year).not()) {
            return BaseModel.failureList(message = "年份有误哦~")
        }
        if (DateUtil.isCurrentMonth(month).not()) {
            return BaseModel.failureList(message = "月份有误哦~")
        }
        val mPage = if (page <= 0) {
            1
        } else page
        val mPageSize = pageSize?: 10
        val offset = (mPage - 1) * mPageSize
        val logs = signInService.getLogsByUserAndPetIdAndYearMonth(petId, year, month, offset, mPageSize)
        val total = signInService.getLogsNumberByPetId(petId, year, month)
        val hasNext = offset + mPageSize < total
        return BaseModel.successList(hasNext, logs)
    }

    @PostMapping("/sign-in")
    fun userSignIn(
        @RequestBody log: UserSignInLog
    ) : BaseModel<Boolean> {
        if (
            DateUtil.isCurrentYear(log.year).not()
            || DateUtil.isCurrentMonth(log.month).not()
            || DateUtil.isCurrentDay(log.day).not()
        ) {
            return BaseModel.failureBoolean(message = "签到时间有误嗷 (=ﾟωﾟ)ﾉ")
        }
        val effectRows = signInService.addLog(log)
        if (effectRows > 0) {
            if (log.logId > 0) {
                return BaseModel.success(data = true)
            }
            return BaseModel.failureBoolean(message = "签到失败了...www")
        }
        return BaseModel.failureBoolean(message = "签到失败了...(；¬д¬)")
    }

    /**
     * 签到奖励 - 货币
     */
    private fun addCurrencyToSignInConfig(
        amount: Int,
        year: Int,
        month: Int,
        day: Int,
        remark: String? = null
    ) : BaseModel<Boolean> {
        val success = signInConfigService.addReward(
            category = 1,
            amount = amount,
            year = year,
            month = month,
            day = day,
            remark = remark?:"签到奖励~~~"
        )
        if (success) {
            return BaseModel.success(data = true)
        }
        return BaseModel.failureBoolean(message = "设置失败了  (;° ロ°)")
    }

    /**
     * 签到奖励 - 道具
     */
    private fun addPropToSignInConfig(
        propId: Int?,
        propType: Int?,
        amount: Int,
        year: Int,
        month: Int,
        day: Int,
        remark: String? = null
    ) : BaseModel<Boolean> {
        if (propId == null || propType == null) {
            return BaseModel.failureBoolean(message = "请确定道具类型~")
        }
        val bpid = propService.getPropId(propType, propId) // 找到主键
        if (bpid < 0) {
            return BaseModel.failureBoolean(message = "没有找到指定类型的道具~")
        }
        val success = signInConfigService.addReward(
            category = 2,
            amount = amount,
            year = year,
            month = month,
            day = day,
            propId = propId,
            propType = propType,
            remark = remark?:"签到奖励~~~"
        )
        if (success) {
            return BaseModel.success(data = true)
        }
        return BaseModel.failureBoolean(message = "设置失败 ,,Ծ‸Ծ,,")
    }
}