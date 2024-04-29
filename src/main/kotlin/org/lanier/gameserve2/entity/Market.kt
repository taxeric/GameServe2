package org.lanier.gameserve2.entity

/**
 * Created by 黄瓜
 * Date 2024/2/14 17:45
 */
data class Market(
    val mid: Int = 0,
    val propId: Int = 0,
    val propType: Int = PropType.SEED,
    val name: String = "",
    val desc: String = "",
    val price: Int = 0,
    val effect: String = "",
)
