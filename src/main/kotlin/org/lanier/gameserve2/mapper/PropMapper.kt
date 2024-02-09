package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface PropMapper {

    fun getProp(propType: Int, propId: Int) : Int

    fun addProp(propType: Int, propId: Int) : Int
}