package org.lanier.gameserve2.service

import org.lanier.gameserve2.mapper.SignInConfigMapper
import org.springframework.stereotype.Service

/**
 * Created by 黄瓜
 * Date 2024/2/9 17:00
 */
@Service
class SignInConfigService(
    private val mapper: SignInConfigMapper
) {

    fun getAllRewardsByYearAndMonth(
        year: Int,
        month: Int,
    ) = mapper.getAllRewardsByYearAndMonth(year, month)

    fun addReward(
        category: Int,
        amount: Int,
        remark: String,
        year: Int,
        month: Int,
        day: Int,
        propId: Int? = null,
        propType: Int? = null
    ) = mapper.addReward(category, amount, remark, year, month, day, propId, propType) > 0
}