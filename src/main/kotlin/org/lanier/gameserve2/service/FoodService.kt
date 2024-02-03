package org.lanier.gameserve2.service

import org.lanier.gameserve2.mapper.FoodMapper
import org.springframework.stereotype.Service

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 0:29
 */
@Service
class FoodService(
    private val mapper: FoodMapper
) {

    fun getFoodById(foodId: Int) = mapper.getFoodById(foodId)

    fun getFoodsByUserAndPetId(userId: Int, petId: Int) = mapper.getFoodsByUserAndPetId(userId, petId)
}