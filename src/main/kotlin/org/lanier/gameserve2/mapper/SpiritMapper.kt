package org.lanier.gameserve2.mapper

import org.lanier.gameserve2.entity.Spirit

/**
 * Created by 黄瓜
 * Date 2024/5/2 15:45
 */
interface SpiritMapper {

    fun obtainSpirit(offset: Int, pageSize: Int) : List<Spirit>

    fun randomSpirit() : Spirit

    fun spiritTotal() : Int
}