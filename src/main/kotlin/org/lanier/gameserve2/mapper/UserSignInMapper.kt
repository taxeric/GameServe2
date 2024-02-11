package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.UserSignInLog

/**
 * Created by 黄瓜
 * Date 2024/2/10 21:01
 */
@Mapper
interface UserSignInMapper {

    fun getLogsByUserAndPetIdAndYearMonth(userId: Int, petId: Int, year: Int, month: Int, offset: Int, pageSize: Int = 10) : List<UserSignInLog>

    fun addLog(log: UserSignInLog) : Int
}