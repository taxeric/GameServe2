package org.lanier.gameserve2.service

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

    fun consume(
        userId: Int,
        petId: Int,
        propId: Int,
        propType: Int,
        consume: Int,
    ) = mapper.consume(userId, petId, propId, propType, consume) > 0
}