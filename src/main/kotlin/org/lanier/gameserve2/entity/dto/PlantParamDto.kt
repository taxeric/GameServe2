package org.lanier.gameserve2.entity.dto

/**
 * Created by 幻弦让叶
 * Date 2024/2/25 1:53
 *
 * 如何计算剩余时间
 * 当作物种下后, 获取当前时间, 加上第一阶段所需要的时间, 单位ms, 存储为[nextStageStartTime],
 * 表示下一阶段的开始时间
 * 每次用户获取种植信息时, 刷新时间, 判定是否到下一阶段
 */
data class PlantParamDto(
    val landId: Int = -1,// r
    val petId: Int = -1,// r
    val seedId: Int = -1,// r
    val bpkId: Int = -1,// r

    /**
     * 状态 1解锁 2种植ing 3空闲
     */
    var status : Int = 0, // 计算

    /**
     * 当前阶段
     */
    var currentStage : Int = 0, // 计算

    /**
     * 最大阶段值
     */
    var maxStage : Int = 0, // 计算

    /**
     * 已收获的次数
     */
    val harvestCount : Int = 0, // 计算

    /**
     * 最大收获次数
     */
    var maxHarvestCount : Int = 0, // 计算

    /**
     * 下一阶段的开始时间
     */
    var nextStageStartTime: Long = 0L, // 计算

    /**
     * 到下一阶段剩余的时间
     */
    var nextStageRemainTime : Int = 0, // 计算

    /**
     * 下一阶段的开始时间点
     */
    var nextStageAllTime: String? = null,//
)
