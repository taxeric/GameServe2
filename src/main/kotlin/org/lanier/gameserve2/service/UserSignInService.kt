package org.lanier.gameserve2.service

import org.lanier.gameserve2.entity.UserSignInLog
import org.lanier.gameserve2.mapper.UserSignInMapper
import org.springframework.stereotype.Service

/**
 * Created by 黄瓜
 * Date 2024/2/10 21:02
 */
@Service
class UserSignInService(
    private val mapper: UserSignInMapper
) {

    /**
     * 获取签到信息
     */
    fun getLogsByUserAndPetIdAndYearMonth(petId: Int, year: Int, month: Int, offset: Int, pageSize: Int = 10)
    = mapper.getLogsByUserAndPetIdAndYearMonth(petId, year, month, offset, pageSize)

    /**
     * 签到数量
     */
    fun getLogsNumberByPetId(petId: Int, year: Int, month: Int) = mapper.getLogsNumberByPetId(petId, year, month)

    /**
     * 签到
     */
    fun addLog(log: UserSignInLog) = mapper.addLog(log)
}