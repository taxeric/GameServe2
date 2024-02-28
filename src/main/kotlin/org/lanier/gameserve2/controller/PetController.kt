package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.cache.CommonCaches
import org.lanier.gameserve2.entity.Pet
import org.lanier.gameserve2.entity.PropType
import org.lanier.gameserve2.entity.articles.Drug
import org.lanier.gameserve2.entity.articles.Food
import org.lanier.gameserve2.entity.articles.Toiletries
import org.lanier.gameserve2.service.*
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
    private val toiletriesService: ToiletriesService,
    private val foodService: FoodService,
    private val drugService: DrugService,
) {

    @PostMapping("/create")
    fun create(
        @RequestParam("name") name: String,
        @RequestParam("userId") userId: String,
    ) : BaseModel<Pet?> {
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
    ) : BaseModel<Pet?> {
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
        val pet = getPet(mPetId)!!
        val amount = myBPKInfos[0].amount // 背包数量
        val mValue = if (CommonCaches.foods.contains(mFoodId)) {
            CommonCaches.foods[mFoodId]!!.value
        } else {
            val mFoods = foodService.getFoodById(mFoodId)
            if (mFoods.isEmpty()) {
                return BaseModel.failureBoolean(message = "没有对应食物哦~")
            }
            val kFood = mFoods[0]
            CommonCaches.foods[mFoodId] = kFood
            kFood.value
        } // 每个用品增加清洁度
        val curSatiety = pet.satiety // 当前饱食度
        val needConsume = calcConsumeAmount(num, amount, mValue, curSatiety, Food.MAX_SATIETY) // 实际需要消耗的物品数量
        if (needConsume.first < 0) {
            return BaseModel.failureBoolean(message = "数量不够哦~")
        }
        if (needConsume.first == 0) {
            return BaseModel.successBool(message = "宠物已经很饱啦, 不需要吃东西哦~")
        }
        val success = bpkService.consume(
            userId = userId.toInt(),
            petId = mPetId,
            propId = mFoodId,
            propType = PropType.FOOD,
            needConsume.first
        )
        if (!success) {
            return BaseModel.failureBoolean(message = "好像出错了~")
        }
        petService.updateStatusOfSatiety(mPetId, needConsume.second)
        return BaseModel.successBool(message = "开吃开吃~")
    }

    @PostMapping("/wash")
    fun washing(
        @RequestParam("petId") petId: String,
        @RequestParam("userId") userId: String,
        @RequestParam("toiletriesId") toiletriesId: String,
        @RequestParam("amount") num: Int,
    ) : BaseModel<Boolean> {
        if (petId.isEmpty() || userId.isEmpty() || toiletriesId.isEmpty()) {
            return BaseModel.failureBoolean(message = "发生错误了...")
        }
        val mPetId = petId.toInt()
        val mToiletriesId = toiletriesId.toInt()
        val myBPKInfos = bpkService.findPropById(
            userId = userId.toInt(),
            petId = mPetId,
            propId = mToiletriesId,
            propType = PropType.TOILETRIES,
        )
        if (myBPKInfos.isEmpty()) {
            return BaseModel.failureBoolean(message = "没有指定洗浴用品哦~")
        }
        if (myBPKInfos.size > 1) {
            return BaseModel.failureBoolean(message = "好像出错了~")
        }
        val pet = getPet(mPetId)!!
        val amount = myBPKInfos[0].amount // 背包数量
        val mValue = if (CommonCaches.toiletries.contains(mToiletriesId)) {
            CommonCaches.toiletries[mToiletriesId]!!.value
        } else {
            val mToiletries = toiletriesService.getToiletriesById(mToiletriesId)
            if (mToiletries.isEmpty()) {
                return BaseModel.failureBoolean(message = "没有对应洗浴用品哦~")
            }
            val kFood = mToiletries[0]
            CommonCaches.toiletries[mToiletriesId] = kFood
            kFood.value
        } // 每个用品增加清洁度
        val curCleanliness = pet.cleanliness // 当前清洁度
        val needConsume = calcConsumeAmount(num, amount, mValue, curCleanliness, Toiletries.MAX_CLEANLINESS) // 实际需要消耗的物品数量
        if (needConsume.first < 0) {
            return BaseModel.failureBoolean(message = "数量不够哦~")
        }
        if (needConsume.first == 0) {
            return BaseModel.successBool(message = "宠物已经很干净啦~")
        }
        val success = bpkService.consume(
            userId = userId.toInt(),
            petId = mPetId,
            propId = mToiletriesId,
            propType = PropType.TOILETRIES,
            needConsume.first
        )
        if (!success) {
            return BaseModel.failureBoolean(message = "好像出错了~")
        }
        petService.updateStatusOfCleanliness(mPetId, needConsume.second)
        return BaseModel.success(true, message = "宠物已经洗干净啦~")
    }

    @PostMapping("/medicate")
    fun medicate(
        @RequestParam("petId") petId: Int,
        @RequestParam("userId") userId: Int,
        @RequestParam("drugId") drugId: Int,
    ) : BaseModel<Boolean> {
        val myBPKInfos = bpkService.findPropById(
            userId = userId,
            petId = petId,
            propId = drugId,
            propType = PropType.DRUG,
        )
        if (myBPKInfos.isEmpty()) {
            return BaseModel.failureBoolean(message = "没有指定药品哦~")
        }
        if (myBPKInfos.size > 1) {
            return BaseModel.failureBoolean(message = "好像出错了~")
        }
        val pet = getPet(petId)!!
        val amount = myBPKInfos[0].amount // 背包数量
        val mValue = if (CommonCaches.drug.contains(drugId)) {
            CommonCaches.drug[drugId]!!.value
        } else {
            val mDrugs = drugService.getDrugById(drugId)
            if (mDrugs.isEmpty()) {
                return BaseModel.failureBoolean(message = "没有对应药品哦~")
            }
            val kFood = mDrugs[0]
            CommonCaches.drug[drugId] = kFood
            kFood.value
        } // 每个用品增加清洁度
        val curSatiety = pet.healthy // 当前饱食度
        val needConsume = calcConsumeAmount(1, amount, mValue, curSatiety, Drug.MAX_HEALTHY) // 实际需要消耗的物品数量
        if (needConsume.first < 0) {
            return BaseModel.failureBoolean(message = "数量不够哦~")
        }
        if (needConsume.first == 0) {
            return BaseModel.successBool(message = "宠物很健康, 不需要吃药哦~")
        }
        val success = bpkService.consume(
            userId = userId,
            petId = petId,
            propId = drugId,
            propType = PropType.DRUG,
            needConsume.first
        )
        if (!success) {
            return BaseModel.failureBoolean(message = "好像出错了~")
        }
        petService.updateStatusOfHealthy(petId, needConsume.second)
        return BaseModel.success(true, message = "宠物吃完药已经好啦~")
    }

    /**
     * 计算需要消耗的物品数量
     *
     * @param num 用户想消耗的物品数量
     * @param amount 背包中存在的数量
     * @param value 一份物品增加的程度值
     * @param curValue 当前已有的程度值
     * @param maxValue 最大可容纳的程度值
     *
     * @return 返回Pair, first-需要消耗的物品数量, second-最终的程度值
     */
    private fun calcConsumeAmount(
        num: Int,
        amount: Int,
        value: Int,
        curValue: Int,
        maxValue: Int
    ) : Pair<Int, Int> {
        if (curValue >= maxValue) { // 无需补充
            return Pair(0, maxValue)
        }
        if (num > amount) { // 背包物品不足
            return Pair(-1, -1)
        }
        val resultValue = curValue + num * value
        if ((resultValue > maxValue).not()) {
            return Pair(num, resultValue)
        }
        val kkValue = maxValue - curValue
        val c1 = kkValue % value
        val c2 = kkValue / value
        val needConsume = (if (c1 != 0) c2 + 1 else c2).toInt() // 达到最大值一共需要消耗的数量
        val rv = curValue + needConsume * value
        return Pair(needConsume, if (rv > maxValue) maxValue else rv)
    }

    private fun getPet(id: Int) : Pet? {
        val pets = petService.getPetById(id)
        if (pets.isEmpty()) {
            return null
        }
        return pets[0]
    }
}