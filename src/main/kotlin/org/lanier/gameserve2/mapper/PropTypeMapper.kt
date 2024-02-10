package org.lanier.gameserve2.mapper

import org.lanier.gameserve2.entity.PropType

/**
 * Created by 黄瓜
 * Date 2024/2/10 00:12
 */
interface PropTypeMapper {

    fun getAllType() : List<PropType>

    fun addType(name: String) : Int
}