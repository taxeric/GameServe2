package org.lanier.gameserve2.entity.articles

data class Food(

    /**
     * 食物id
     */
    val foodId: Int = 0,

) : CommonArticles() {

    companion object {

        const val MAX_SATIETY = 100
    }
}