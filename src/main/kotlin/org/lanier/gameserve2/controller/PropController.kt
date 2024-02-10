package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.PropType
import org.lanier.gameserve2.entity.Season
import org.lanier.gameserve2.entity.articles.Drug
import org.lanier.gameserve2.entity.articles.Food
import org.lanier.gameserve2.entity.articles.Toiletries
import org.lanier.gameserve2.service.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by 黄瓜
 * Date 2024/2/10 00:10
 */
@RestController
@RequestMapping("/prop")
class PropController(
    private val typeService: PropTypeService,
    private val propService: PropService,
    private val foodService: FoodService,
    private val toiletriesService: ToiletriesService,
    private val drugService: DrugService,
) {

    /**
     * 获取全部类型
     */
    @GetMapping("/getAllType")
    fun getAllProp() : BaseModel<List<PropType>> {
        val data = typeService.getAllType()
        return BaseModel.success(data = data)
    }

    /**
     * 添加类型
     */
    @PostMapping("/addType")
    fun addPropType(
        @RequestParam("name") name: String
    ) : BaseModel<Boolean> {
        val success = typeService.addType(name)
        if (success) {
            return BaseModel.success(data = true)
        }
        return BaseModel.failureBoolean(message = "添加失败了... ╥﹏╥")
    }

    @GetMapping("/getAllFoods")
    fun getAllFood() : BaseModel<List<Food>> {
        val foods = foodService.getAllFoods()
        return BaseModel.success(data = foods)
    }

    @PostMapping("/addFood")
    fun addProp(
        @RequestBody food: Food
    ) : BaseModel<Boolean> {
        val effectRows = foodService.addFood(food)
        if (effectRows > 0) {
            val success = addToProp(food.name, food.pic, PropType.FOOD, food.foodId)
            if (success) {
                return BaseModel.success(data = true)
            }
            return BaseModel.failureBoolean(message = "添加到道具失败了, 看一下咋回事")
        }
        return BaseModel.failureBoolean(message = "添加食物失败了...")
    }

    @GetMapping("/getAllToiletries")
    fun getAllToiletries() : BaseModel<List<Toiletries>> {
        val toiletries = toiletriesService.getAllToiletries()
        return BaseModel.success(data = toiletries)
    }

    @PostMapping("/addToiletries")
    fun addProp(
        @RequestBody toiletries: Toiletries
    ) : BaseModel<Boolean> {
        val effectRows = toiletriesService.addToiletries(toiletries)
        if (effectRows > 0) {
            val success = addToProp(toiletries.name, toiletries.pic, PropType.TOILETRIES, toiletries.toiletriesId)
            if (success) {
                return BaseModel.success(data = true)
            }
            return BaseModel.failureBoolean(message = "添加到道具失败了, 看一下咋回事")
        }
        return BaseModel.failureBoolean(message = "添加洗浴用品失败了...")
    }

    @GetMapping("/getAllDrugs")
    fun getAllDrugs() : BaseModel<List<Drug>> {
        val drugs = drugService.getAllDrugs()
        return BaseModel.success(data = drugs)
    }

    @PostMapping("/addDrug")
    fun addProp(
        @RequestBody drug: Drug
    ) : BaseModel<Boolean> {
        val effectRows = drugService.addDrug(drug)
        if (effectRows > 0) {
            val success = addToProp(drug.name, drug.pic, PropType.DRUG, drug.drugId)
            if (success) {
                return BaseModel.success(data = true)
            }
            return BaseModel.failureBoolean(message = "添加到道具失败了, 看一下咋回事")
        }
        return BaseModel.failureBoolean(message = "添加食物失败了...")
    }

    /**
     * 添加到道具表
     */
    private fun addToProp(
        propName: String,
        propPic: String,
        propType: Int,
        propRealId: Int,
    ) : Boolean {
        return propService.addProp(
            realPropId = propRealId,
            propType = propType,
            propName = propName,
            propPic = propPic
        )
    }
}