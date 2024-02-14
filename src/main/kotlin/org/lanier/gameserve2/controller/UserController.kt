package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.User
import org.lanier.gameserve2.service.PetService
import org.lanier.gameserve2.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val service: UserService,
    private val petService: PetService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam("account") account: String,
        @RequestParam("password") password: String
    ) : BaseModel<User> {
        val users = service.login(account, password)
        if (users.isEmpty()) {
            return BaseModel.failure(message = "未找到用户~")
        }
        if (users.size == 1) {
            val user = users[0].copy(
                pets = getPets(users[0].userId)
            )
            return BaseModel.success(data = user)
        }
        return BaseModel.failure(message = "发生异常~")
    }

    @PostMapping("/register")
    fun register(
        @RequestParam("account") account: String,
        @RequestParam("password") password: String
    ) : BaseModel<Boolean> {
        val result = service.register(account, password)
        return if (result > 0) {
            BaseModel.success(message = "注册成功~", data = true)
        } else {
            BaseModel.failure(message = "注册失败~", data = false)
        }
    }

    @PostMapping("/getInfo")
    fun getInfo(
        @RequestParam("userId") userId: String
    ) : BaseModel<User> {
        val uid = try {
            userId.toInt()
        } catch (e: Throwable) {
            -1
        }
        if (uid <= 0) {
            return BaseModel.failure(message = "获取失败, 请检查用户id~")
        }
        val result = service.getUserById(uid)
        if (result.isEmpty()) {
            return BaseModel.success(data = null, message = "没有找到用户信息~")
        }
        val user = result[0].copy(
            pets = getPets(result[0].userId)
        )
        return BaseModel.success(data = user)
    }

    private fun getPets(userId: Int) = petService.getPetsByUserId(userId)
}