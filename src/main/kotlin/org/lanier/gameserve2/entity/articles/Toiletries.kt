package org.lanier.gameserve2.entity.articles

/**
 * 洗漱用品
 */
data class Toiletries(

    /**
     * 洗漱用品id
     */
    val toiletriesId: Int
) : CommonArticles() {

    companion object {

        const val MAX_CLEANLINESS = 100
    }
}
