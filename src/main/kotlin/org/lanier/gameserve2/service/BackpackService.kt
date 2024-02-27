package org.lanier.gameserve2.service

import org.lanier.gameserve2.entity.Backpack
import org.lanier.gameserve2.entity.dto.BackpackDto
import org.lanier.gameserve2.mapper.BackpackMapper
import org.springframework.stereotype.Service

/**
 * Created by 幻弦让叶
 * Date 2024/2/2 0:46
 */
@Service
class BackpackService(
    private val mapper: BackpackMapper
) {

    fun findPropById(
        userId: Int,
        petId: Int,
        propId: Int,
        propType: Int
    ) = mapper.findPropById(userId, petId, propId, propType)

    fun getQualityById(bpkId: Int?): Int? {
        return mapper.getQualityById(bpkId!!)
    }

    fun getQualityByTypeId(petId: Int, type: Int, realPropId: Int): Int {
        return mapper.getQualityByTypeId(petId, type, realPropId)
    }

    fun getSeedTotal(petId: Int): Int {
        return mapper.getSeedTotal(petId)
    }

    fun getFertilizerTotal(petId: Int): Int {
        return mapper.getFertilizerTotal(petId)
    }

    fun getCropTotal(petId: Int): Int {
        return mapper.getCropTotal(petId)
    }

    fun getSeedsByPid(petId: Int, offset: Int, pageSize: Int): List<BackpackDto> {
        return mapper.getSeedsByUid(petId, offset, pageSize)
    }

    fun getFertilizerByUid(userId: Int?, offset: Int, pageSize: Int): List<BackpackDto> {
        return mapper.getFertilizerByUid(userId!!, offset, pageSize)
    }

    fun getCropsByUid(userId: Int?, offset: Int, pageSize: Int): List<BackpackDto> {
        return mapper.getCropsByUid(userId!!, offset, pageSize)
    }

    fun consume(
        userId: Int,
        petId: Int,
        propId: Int,
        propType: Int,
        consume: Int,
    ) = mapper.consume(userId, petId, propId, propType, consume) > 0

    fun addProp(backpack: Backpack): Boolean {
        return mapper.addProp(backpack) == 1
    }

    fun updateProp(backpack: Backpack): Boolean {
        return mapper.updateProp(backpack) == 1
    }

    fun sellProp(type: Int, realPropId: Int, quantity: Int, userid: Int): Boolean {
        return mapper.sellProp(type, realPropId, quantity, userid) == 1
    }
}