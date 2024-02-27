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
class PlantParamDto {
    private val landId: Int? = null // r
    private val userId: Int? = null // r
    private val seedId: Int? = null // r
    private val bpkId: Int? = null // r

    /**
     * 状态 1解锁 2种植ing 3空闲
     */
    private val status = 0 // 计算

    /**
     * 当前阶段
     */
    private val currentStage = 0 // 计算

    /**
     * 最大阶段值
     */
    private val maxStage = 0 // 计算

    /**
     * 已收获的次数
     */
    private val harvestCount = 0 // 计算

    /**
     * 最大收获次数
     */
    private val maxHarvestCount = 0 // 计算

    /**
     * 下一阶段的开始时间
     */
    private val nextStageStartTime: Long = 0 // 计算

    /**
     * 到下一阶段剩余的时间
     */
    private val nextStageRemainTime = 0 // 计算

    /**
     * 下一阶段的开始时间点
     */
    private val nextStageAllTime: String? = null //
}
