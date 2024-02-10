package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.articles.Toiletries
import org.lanier.gameserve2.entity.dto.BackpackDTO

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 14:42
 */
@Mapper
interface ToiletriesMapper {

    fun getToiletriesById(toiletriesId: Int) : List<Toiletries>

    fun getToiletriesByUserAndPetId(userId: Int, petId: Int) : List<BackpackDTO>

    fun getAllToiletries() : List<Toiletries>

    fun addToiletries(toiletries: Toiletries) : Int
}