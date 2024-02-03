package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.Backpack

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
        userId: Int,
        petId: Int,
        propId: Int,
        propType: Int,
        consume: Int,
    ) : Int
}