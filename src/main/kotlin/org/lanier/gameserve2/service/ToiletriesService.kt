package org.lanier.gameserve2.service

import org.lanier.gameserve2.mapper.ToiletriesMapper
import org.springframework.stereotype.Service

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 14:57
 */
@Service
class ToiletriesService(
    private val mapper: ToiletriesMapper
) {

    fun getToiletriesById(toiletriesId: Int) = mapper.getToiletriesById(toiletriesId)
}