package org.lanier.gameserve2.entity

data class Backpack(

    /**
     * 道具id
     */
    val propId: Int = 0,

    /**
     * 道具名称
     */
    val name: String = "",

    /**
     * 道具数量
     */
    val amount: Int = 0,

    /**
     * 道具类型
     */
    val type: Int = 0,

    /**
     * 用户id
     */
    val userId: Int = 0,

    /**
     * 宠物id
     */
    val petId: Int = 0,
)
