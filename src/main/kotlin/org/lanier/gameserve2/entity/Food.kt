package org.lanier.gameserve2.entity

data class Food(

    /**
     * 食物id
     */
    val foodId: Int = 0,

    /**
     * 食物名称
     */
    val name: String = "",

    /**
     * 价格
     */
    val cost: Int = 0,

    /**
     * 食用后增加的饱腹度
     */
    val value: Int = 0,

    /**
     * 链接
     */
    val pic: String = "",

    /**
     * 所属季节
     */
    val season: Int = 0,
)