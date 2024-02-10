package org.lanier.gameserve2.service

import org.lanier.gameserve2.entity.articles.Drug
import org.lanier.gameserve2.mapper.DrugMapper
import org.springframework.stereotype.Service

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 14:57
 */
@Service
class DrugService(
    private val mapper: DrugMapper
) {

    fun getDrugById(drugId: Int) = mapper.getDrugById(drugId)

    fun getDrugByUserAndPetId(userId: Int, petId: Int) = mapper.getDrugByUserAndPetId(userId, petId)

    fun getAllDrugs() = mapper.getAllDrugs()

    fun addDrug(drug: Drug) = mapper.addDrug(drug)
}