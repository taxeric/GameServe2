package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.SignInConfig
import org.lanier.gameserve2.entity.dto.SignInConfigDTO

/**
 * Created by 黄瓜
 * Date 2024/2/9 16:59
 */
@Mapper
interface SignInConfigMapper {

    fun getAllRewardsByYearAndMonth(
        year: Int,
        month: Int
    ) : List<SignInConfigDTO>

    fun addReward(
        category: Int,
        amount: Int,
        remark: String,
        year: Int,
        month: Int,
        day: Int,
        propId: Int?,
        propType: Int?
    ) : Int
}