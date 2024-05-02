package org.lanier.gameserve2.service

import org.lanier.gameserve2.entity.Spirit
import org.lanier.gameserve2.mapper.SpiritMapper
import org.springframework.stereotype.Service

@Service
class SpiritService(
    private val mapper: SpiritMapper
) {

    fun obtainSpirit(offset: Int, pageSize: Int) : List<Spirit> = mapper.obtainSpirit(offset, pageSize)

    fun randomSpirit() : Spirit = mapper.randomSpirit()

    fun spiritTotal() : Int = mapper.spiritTotal()
}