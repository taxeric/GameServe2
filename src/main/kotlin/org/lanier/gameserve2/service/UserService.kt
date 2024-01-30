package org.lanier.gameserve2.service

import org.lanier.gameserve2.mapper.UserMapper
import org.springframework.stereotype.Service

@Service
class UserService(
    private val mapper: UserMapper
) {

    fun login(account: String, password: String) = mapper.login(account, password)

    fun register(account: String, password: String) = mapper.register(account, password)

    fun getUserById(userId: Int) = mapper.getUserById(userId)
}