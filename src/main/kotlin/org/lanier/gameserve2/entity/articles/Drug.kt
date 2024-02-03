package org.lanier.gameserve2.entity.articles

/**
 * 药品
 */
data class Drug(

    /**
     * 药品id
     */
    val drugId: Int
) : CommonArticles() {

    companion object {

        const val MAX_HEALTHY = 100
    }
}