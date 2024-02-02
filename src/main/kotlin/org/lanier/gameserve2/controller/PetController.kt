package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.cache.CommonCaches
import org.lanier.gameserve2.entity.Pet
import org.lanier.gameserve2.entity.PropType
import org.lanier.gameserve2.service.BackpackService
import org.lanier.gameserve2.service.FoodService
import org.lanier.gameserve2.service.PetService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pet")
class PetController(
    private val petService: PetService,
    private val bpkService: BackpackService,
    private val foodService: FoodService,
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
        val result = petService.create(pet)
        if (result > 0) {
            getPet(pet.petId)?.let {
                return BaseModel.success(data = it)
            } ?: return BaseModel.failure(message = "发生错误了...")
        }
        return BaseModel.failure(message = "发生错误了...")
    }

    @GetMapping("/info")
    fun getPetInfo(
        @RequestParam("petId") petId: String,
    ) : BaseModel<Pet> {
        if (petId.isEmpty()) {
            return BaseModel.failure(message = "没有找到宠物哦~")
        }
        val mPid = try {
            petId.toInt()
        } catch (e: Throwable) {
            -1
        }
        if (mPid < 0) {
            return BaseModel.failure(message = "没有找到宠物哦~")
        }
        val pet = getPet(mPid) ?: return BaseModel.failure(message = "没有找到宠物哦~")
        return BaseModel.success(pet)
    }

    @PostMapping("/eat")
    fun eat(
        @RequestParam("petId") petId: String,
        @RequestParam("userId") userId: String,
        @RequestParam("foodId") foodId: String,
        @RequestParam("amount") num: Int,
    ) : BaseModel<Boolean> {
        if (petId.isEmpty() || userId.isEmpty() || foodId.isEmpty()) {
            return BaseModel.failureBoolean(message = "发生错误了...")
        }
        val mPetId = petId.toInt()
        val mFoodId = foodId.toInt()
        val myBPKInfos = bpkService.findPropById(
            userId = userId.toInt(),
            petId = mPetId,
            propId = mFoodId,
            propType = PropType.FOOD,
        )
        if (myBPKInfos.isEmpty()) {
            return BaseModel.failureBoolean(message = "没有指定食物哦~")
        }
        if (myBPKInfos.size > 1) {
            return BaseModel.failureBoolean(message = "好像出错了~")
        }
        val amount = myBPKInfos[0].amount
        if (amount >= num) {
            val success = bpkService.consume(
                userId = userId.toInt(),
                petId = mPetId,
                propId = mFoodId,
                propType = PropType.FOOD,
                amount - num
            )
            if (!success) {
                return BaseModel.failureBoolean(message = "好像出错了~")
            }
        } else {
            return BaseModel.failureBoolean(message = "数量不够哦~")
        }
        val pet = getPet(mPetId)!!
        val mValue = if (CommonCaches.foods.contains(mFoodId)) {
            CommonCaches.foods[mFoodId]!!.value * num
        } else {
            val mFoods = foodService.getFoodById(mFoodId)
            if (mFoods.isEmpty()) {
                return BaseModel.failureBoolean(message = "没有对应食物哦~")
            }
            val kFood = mFoods[0]
            CommonCaches.foods[mFoodId] = kFood
            kFood.value * num
        }
        var resultValue = pet.satiety + mValue
        if (resultValue > 100) resultValue = 100
        petService.updateStatusOfSatiety(mPetId, resultValue)
        return BaseModel.success(true)
    }

    private fun getPet(id: Int) : Pet? {
        val pets = petService.getPetById(id)
        if (pets.isEmpty()) {
            return null
        }
        return pets[0]
    }
}