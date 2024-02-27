package org.lanier.gameserve2.entity.dto

/**
 * Created by 黄瓜
 * Date 2024/2/14 17:36
 */
class SeedDto {
    private val seedId: Int? = null

    private val name: String? = null

    private val type: Int? = null

    private val season = 0

    /**
     * 最大收获次数
     */
    private val seedMaxHarvestCount = 0

    /**
     * 预计收获获得的总经验值 = (单词收获的数量 * 单个作物收获的经验值) * 最大收获次数
     */
    private val totalHarvestExp = 0

    /**
     * 单个成熟作物收获的经验值
     */
    private val harvestExp = 0

    /**
     * 单次收获的作物数量
     */
    private val harvestAmount = 0

    /**
     * 阶段序列
     * 内容为{stageName:[阶段1, 阶段2, 阶段3],stageSustainTime:[1800, 1800, 1800]}, 分别表示阶段名称 & 阶段持续时间, 数组长度一一对应
     */
    private val stageInfo: String? = null
}
