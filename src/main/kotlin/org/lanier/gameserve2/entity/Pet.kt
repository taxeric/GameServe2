package org.lanier.gameserve2.entity

import java.util.Date

data class Pet(

    /**
     * 宠物id
     */
    val petId: Int = 0,

    /**
     * 用户id
     */
    val userId: Int = 0,

    /**
     * 名称
     */
    val name: String = "",

    /**
     * 年龄
     */
    val age: Int = 0,

    /**
     * 饱腹度
     */
    val satiety: Int = 0,

    /**
     * 清洁度
     */
    val cleanliness: Int = 0,

    /**
     * 智力
     */
    val intelligence: Int = 0,

    /**
     * 体力
     */
    val muscle: Int = 0,

    /**
     * 健康度
     */
    val healthy: Int = 0,

    /**
     * 心情
     */
    val emotion: Int = 0,

    /**
     * 生日
     */
    val birthday: Date? = null,

    /**
     * 阶段, 分为幼年, 少年, 成年
     */
    val phase: Int = 0,

    /**
     * 当前种植经验
     */
    val currentPlantExp: Int = 0,

    /**
     * 当前种植等级id
     */
    val currentPlantLevelId: Int = 0,
)
