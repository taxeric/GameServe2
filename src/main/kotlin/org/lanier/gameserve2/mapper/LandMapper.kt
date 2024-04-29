package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.Land
import org.lanier.gameserve2.entity.dto.LandDto
import org.lanier.gameserve2.entity.dto.PlantLevelDto
import org.lanier.gameserve2.entity.dto.PlantParamDto
import org.lanier.gameserve2.entity.dto.SeedDto

/**
 * Created by 幻弦让叶
 * Date 2024/2/24 21:04
 */
@Mapper
interface LandMapper {
    fun getLandInfosByPetId(petId: Int): List<LandDto>

    fun getLandInfoByLid(landId: Int): List<LandDto>

    fun newPetCreated(lands: List<Land>): Int

    fun plantCrop(plantParamDto: PlantParamDto): Int
    fun harvestCrop(landId: Int, petId: Int): Int
    fun updateLandInfo(plantParamDto: PlantParamDto): Int
    fun getSeedInfoById(seedId: Int): List<SeedDto>

    fun getLevelInfo(): List<PlantLevelDto>
}
