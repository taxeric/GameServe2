package org.lanier.gameserve2.service

import org.lanier.gameserve2.mapper.PropMapper
import org.springframework.stereotype.Service

@Service
class PropService(
    private val mapper: PropMapper
) {

    fun getPropId(propType: Int, realPropId: Int) = mapper.getPropId(propType, realPropId)

    fun addProp(propType: Int, realPropId: Int, propName: String, propPic: String)
    = mapper.addProp(propType, realPropId, propName, propPic) > 0
}