package org.lanier.gameserve2.service

import org.lanier.gameserve2.entity.Land
import org.lanier.gameserve2.entity.dto.LandDto
import org.lanier.gameserve2.entity.dto.PlantLevelDto
import org.lanier.gameserve2.entity.dto.PlantParamDto
import org.lanier.gameserve2.entity.dto.SeedDto
import org.lanier.gameserve2.mapper.LandMapper
import org.springframework.stereotype.Service

/**
 * Created by 幻弦让叶
 * Date 2024/2/24 21:52
 */
@Service
class LandService(
    private val landMapper: LandMapper
) {
    fun getLandInfosByPetId(petId: Int): List<LandDto> {
        return landMapper.getLandInfosByPetId(petId)
    }

    fun getLandInfoByLid(landId: Int): List<LandDto> {
        return landMapper.getLandInfoByLid(landId)
    }

    fun newUserCreated(lands: List<Land>): Int {
        return landMapper.newPetCreated(lands)
    }

    fun plantCrop(plantParamDto: PlantParamDto): Boolean {
        return landMapper.plantCrop(plantParamDto) == 1
    }

    fun harvestCrop(landId: Int, petId: Int): Boolean {
        return landMapper.harvestCrop(landId, petId) == 1
    }

    fun updateLandInfo(plantParamDto: PlantParamDto): Boolean {
        return landMapper.updateLandInfo(plantParamDto) == 1
    }

    fun getSeedInfoById(seedId: Int): List<SeedDto> {
        return landMapper.getSeedInfoById(seedId)
    }

    fun levelInfo() = landMapper.levelInfo()
}
