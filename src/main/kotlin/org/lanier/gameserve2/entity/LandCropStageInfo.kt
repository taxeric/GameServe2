package org.lanier.gameserve2.entity

/**
 * Created by 幻弦让叶
 * Date 2024/2/25 16:28
 */
data class LandCropStageInfo(
    /**
     * 下一阶段的开始时间, 单位ms
     */
    private var nextStageAllTime: List<Long>? = null
)
