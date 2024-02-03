package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.articles.Food
import org.lanier.gameserve2.entity.dto.BackpackDTO

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 0:29
 */
@Mapper
interface FoodMapper {

    fun getFoodById(foodId: Int) : List<Food>

    fun getFoodsByUserAndPetId(userId: Int, petId: Int) : List<BackpackDTO>
}