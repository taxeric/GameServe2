package org.lanier.gameserve2.cache

import org.lanier.gameserve2.entity.articles.Clothes
import org.lanier.gameserve2.entity.articles.Drug
import org.lanier.gameserve2.entity.articles.Food
import org.lanier.gameserve2.entity.articles.Toiletries

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 0:17
 */
object CommonCaches {

    /**
     * 食物
     */
    val foods = mutableMapOf<Int, Food>()

    /**
     * 洗浴用品
     */
    val toiletries = mutableMapOf<Int, Toiletries>()

    /**
     * 药品
     */
    val drug = mutableMapOf<Int, Drug>()

    /**
     * 服饰
     */
    val clothes = mutableMapOf<Int, Clothes>()
}