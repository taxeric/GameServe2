package org.lanier.gameserve2.service

import org.lanier.gameserve2.entity.dto.MarketDto
import org.lanier.gameserve2.mapper.MarketMapper
import org.springframework.stereotype.Service

/**
 * Created by 幻弦让叶
 * Date 2024/2/24 18:52
 */
@Service
class MarketService(
    private val mapper: MarketMapper
) {
    fun seedTotal() = mapper.seedTotal()

    fun fertilizerTotal () = mapper.fertilizerTotal()

    fun getSeeds(offset: Int, pageSize: Int): List<MarketDto> {
        return mapper.getSeeds(offset, pageSize)
    }

    fun getFertilizer(offset: Int, pageSize: Int): List<MarketDto> {
        return mapper.getFertilizer(offset, pageSize)
    }

    fun getItemById(itemId: Int): List<MarketDto> {
        return mapper.getItemById(itemId)
    }
}
