package org.lanier.gameserve2.service

import org.lanier.gameserve2.mapper.PropMapper
import org.springframework.stereotype.Service

@Service
class PropService(
    private val mapper: PropMapper
) {

    fun getProp(propType: Int, propId: Int) = mapper.getProp(propType, propId)

    fun addProp(propType: Int, propId: Int) = mapper.addProp(propType, propId)
}