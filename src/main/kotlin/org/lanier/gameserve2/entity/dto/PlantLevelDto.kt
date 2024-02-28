package org.lanier.gameserve2.entity.dto

/**
 * Created by 黄瓜
 * Date 2024/2/14 17:34
 */
data class PlantLevelDto(
    /**
     * 等级
     */
    val plantLevel : Int = 0,

    /**
     * 填满等级需要的经验值
     */
    val expRequired : Int = 0
)
