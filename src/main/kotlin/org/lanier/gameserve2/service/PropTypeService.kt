package org.lanier.gameserve2.service

import org.lanier.gameserve2.mapper.PropTypeMapper
import org.springframework.stereotype.Service

/**
 * Created by 黄瓜
 * Date 2024/2/10 00:12
 */
@Service
class PropTypeService(
    private val mapper: PropTypeMapper
) {

    fun getAllType() = mapper.getAllType()

    fun addType(name: String) = mapper.addType(name) > 0
}