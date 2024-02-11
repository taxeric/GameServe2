package org.lanier.gameserve2.entity

import java.sql.Timestamp

/**
 * Created by 黄瓜
 * Date 2024/2/10 20:56
 *
 * 用户签到记录
 */
data class UserSignInLog(
    val logId: Int = 0,
    val userId: Int = 0,
    val petId: Int = 0,
    val category: Int = 1, // 1货币, 2道具
    val rewardId: Int? = 0,
    val rewardType: Int? = 0,
    val signInTime: Timestamp? = null,
    val signInType: Int = 1, // 1正常签到, 2补签
    val year: Int = 0,
    val month: Int = 0,
    val day: Int = 0,
)