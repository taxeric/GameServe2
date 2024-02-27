package org.lanier.gameserve2.entity.dto


/**
 * Created by 幻弦让叶
 * Date 2024/2/24 21:10
 */
class LandDto {
    private val landId: Int? = null
    private val userId: Int? = null
    private val bpkId: Int? = null // 背包道具id
    private val cropId: Int? = null
    private val seed: SeedDto? = null // 当前种植的作物
    private val status = 0 // 土地状态, 1=未解锁, 2=种植中, 3=空闲
    private val currentStage = 0 // 作物当前所处的阶段
    private val maxStage = 0 // 最大阶段
    private val nextStageAllTime: String? = null // 下一阶段开始时间点, json字符串
    private val nextStageStartTime: Long = 0 // 下一阶段的开始时间, 单位ms
    private val nextStageRemainTime = 0 // 到下一阶段剩余时间, 单位s
    private val lastHarvestTime: Long = 0 // 上一次收获时间
    private val maxHarvestCount = 0 // 作物最大可收获次数
    private val harvestCount = 0 // 作物已收获次数
}
