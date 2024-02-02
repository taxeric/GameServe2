package org.lanier.gameserve2.entity

/**
 * 道具类型
 */
data class PropType(
    val id: Int = ACTIVITY,
    val name: String = "",
) {

    companion object {

        /**
         * 食物
         */
        const val FOOD = 1

        /**
         * 洗浴用品
         */
        const val TOILETRIES = 2

        /**
         * 药品
         */
        const val DRUG = 3

        /**
         * 服饰
         */
        const val CLOTHE = 4

        /**
         * 活动用品
         */
        const val ACTIVITY = 5
    }
}
