package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface PropMapper {

    fun getPropId(propType: Int, realPropId: Int) : Int

    fun addProp(propType: Int, realPropId: Int, propName: String, propPic: String) : Int
}