package org.lanier.gameserve2.entity.dto

import org.lanier.gameserve2.entity.SpiritAction

/**
 * Created by 黄瓜
 * Date 2024/5/2 15:59
 */
data class SpiritDto(
    val id: Int = -1,
    val name: String = "",
    val preview: String = "",
    val actions: SpiritAction? = null,
)
