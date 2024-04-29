package org.lanier.gameserve2.entity.dto

import org.lanier.gameserve2.entity.SignInConfig

/**
 * Created by 黄瓜
 * Date 2024/2/9 17:27
 */
data class SignInConfigDto(
    val id: Int = 0,
    val category: Int = SignInConfig.CATEGORY_PROP,
    val amount: Int = 1,
    val remark: String = "",
    val propName: String = "",
    val propPic: String = "",
)