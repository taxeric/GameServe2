package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.Pet
import org.lanier.gameserve2.service.PetService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pet")
class PetController(
    private val service: PetService
) {

    @PostMapping("/create")
    fun create(
        @RequestParam("name") name: String,
        @RequestParam("userId") userId: String,
    ) : BaseModel<Pet> {
        if (name.isEmpty()) {
            return BaseModel.failure(message = "给宠物起个名字吧~")
        }
        if (userId.isEmpty()) {
            return BaseModel.failure(message = "没有找到它的主人哟~")
        }
        val uid = try {
            userId.toInt()
        } catch (e: Throwable) {
            -1
        }
        if (uid < 0) {
            return BaseModel.failure(message = "没有找到它的主人哟~")
        }
        val pet = Pet(
            name = name,
            userId = uid
        )
        val result = service.create(pet)
        if (result > 0) {
            val pets = service.getPetById(pet.petId)
            if (pets.isEmpty()) {
                return BaseModel.failure(message = "发生错误了...")
            }
            return BaseModel.success(data = pets[0])
        }
        return BaseModel.failure(message = "发生错误了...")
    }
}