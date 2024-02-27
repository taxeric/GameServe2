package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.dto.MarketDto

/**
 * Created by 幻弦让叶
 * Date 2024/2/24 18:42
 */
@Mapper
interface MarketMapper {
    fun seedTotal(): Int
    fun fertilizerTotal(): Int

    fun getSeeds(offset: Int, pageSize: Int): List<MarketDto>
    fun getFertilizer(offset: Int, pageSize: Int): List<MarketDto>

    fun getItemById(itemId: Int): List<MarketDto>
}
