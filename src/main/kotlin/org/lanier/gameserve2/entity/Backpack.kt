package org.lanier.gameserve2.entity

/**
 * 背包
 *
 * 数据表中[realPropId]和[type]两列确定唯一
 */
data class Backpack(

    /**
     * 道具id
     */
    val propId: Int = 0,

    /**
     * 道具数量
     */
    val amount: Int = 0,

    /**
     * 道具类型
     */
    val type: Int = 0,

    /**
     * 实际指定类型的道具id, 如果type是food, 表示foodId
     */
    val realPropId: Int = 0,

    /**
     * 用户id
     */
    val userId: Int = 0,

    /**
     * 宠物id
     */
    val petId: Int = 0,
)
