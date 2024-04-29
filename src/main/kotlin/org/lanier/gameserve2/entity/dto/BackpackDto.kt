package org.lanier.gameserve2.entity.dto

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 18:52
 */
data class BackpackDto(
    val bpkId: Int = 0, // 对应背包内的id
    val realPropId: Int = 0, // 对应道具id
    val userId: Int = 0,
    val petId: Int = 0,
    val name: String = "",
    val pic: String = "",
    val useLevel: Int = 0,
    val LE: Boolean = false,
    val amount: Int = 0,
    val effect: String = "",
    val effectValue: Int? = 0,
    val sellPrice: Int = 0,
)
