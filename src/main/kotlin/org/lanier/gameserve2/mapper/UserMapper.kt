package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.User

@Mapper
interface UserMapper {

    fun login(account: String, password: String) : List<User>
    fun register(account: String, password: String) : Int
    fun getUserById(userId: Int) : List<User>
}