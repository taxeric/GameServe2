package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.Backpack
import org.lanier.gameserve2.entity.dto.BackpackDto

/**
 * Created by 幻弦让叶
 * Date 2024/2/2 0:48
 */
@Mapper
interface BackpackMapper {

    fun findPropById(
        userId: Int,
        petId: Int,
        propId: Int,
        propType: Int
    ) : List<Backpack>

    fun consume(
        petId: Int,
        propId: Int,
        propType: Int,
        consume: Int,
    ) : Int

    fun getQualityById(bpkId: Int): Int

    fun getQualityByTypeId(petId: Int, type: Int, realPropId: Int): Int?

    fun getSeedTotal(petId: Int): Int
    fun getFertilizerTotal(petId: Int): Int
    fun getCropTotal(petId: Int): Int

    fun getSeedsByPid(petId: Int, offset: Int, pageSize: Int): List<BackpackDto>
    fun getFertilizerByPid(petId: Int, offset: Int, pageSize: Int): List<BackpackDto>
    fun getCropsByPid(petId: Int, offset: Int, pageSize: Int): List<BackpackDto>

    fun addProp(backpack: Backpack): Int

    fun updateProp(backpack: Backpack): Int

    fun sellProp(type: Int, realPropId: Int, quantity: Int, petId: Int): Int
}