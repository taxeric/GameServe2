package org.lanier.gameserve2.entity

/**
 * Created by 黄瓜
 * Date 2024/2/9 17:07
 */
data class SignInConfig(
    val id: Int = 0,
    val category: Int = CATEGORY_PROP,
    val amount: Int = 1,
    val remark: String = "",
    val year: Int = 0,
    val month: Int = 0,
    val propId: Int = -1,
    val propType: Int = -1,
) {

    companion object {

        const val CATEGORY_CURRENCY = 1
        const val CATEGORY_PROP = 2
    }
}