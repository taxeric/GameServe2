package org.lanier.gameserve2.entity.dto


/**
 * Created by 幻弦让叶
 * Date 2024/2/24 21:10
 */
data class LandDto(
    val landId: Int = -1,
    val userId: Int = -1,
    val bpkId: Int = -1, // 背包道具id
    val cropId: Int = -1,
    val seed: SeedDto? = null, // 当前种植的作物
    val status : Int = 0, // 土地状态, 1=未解锁, 2=种植中, 3=空闲
    val currentStage : Int = 0, // 作物当前所处的阶段
    val maxStage : Int = 0, // 最大阶段
    val nextStageAllTime: String = "", // 下一阶段开始时间点, json字符串
    val nextStageStartTime: Long = 0L, // 下一阶段的开始时间, 单位ms
    val nextStageRemainTime : Int = 0, // 到下一阶段剩余时间, 单位s
    val lastHarvestTime: Long = 0L, // 上一次收获时间
    val maxHarvestCount : Int = 0, // 作物最大可收获次数
    val harvestCount : Int = 0, // 作物已收获次数
)
