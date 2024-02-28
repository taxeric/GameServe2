package org.lanier.gameserve2.entity.dto

/**
 * Created by 幻弦让叶
 * Date 2024/2/24 22:36
 */
data class PetDto(
    val userId: Int = -1,
    val petId: Int = -1,
    val petName: String = "",
    val currentPlantExp : Int = 0,
    val lackExp : Int = 0,
    val currentLevel: PlantLevelDto = PlantLevelDto(),
    val coin : Int = 0,
    val unlockedLandCount : Int = 0,
    val usedLandCount : Int = 0,
    val maxLandCount : Int = 0,
)
