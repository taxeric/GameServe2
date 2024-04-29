package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.articles.Drug
import org.lanier.gameserve2.entity.dto.BackpackDto

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 14:42
 */
@Mapper
interface DrugMapper {

    fun getDrugById(drugId: Int) : List<Drug>

    fun getDrugByUserAndPetId(userId: Int, petId: Int) : List<BackpackDto>

    fun getAllDrugs() : List<Drug>

    fun addDrug(drug: Drug) : Int
}