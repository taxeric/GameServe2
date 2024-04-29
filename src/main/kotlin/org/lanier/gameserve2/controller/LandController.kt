package org.lanier.gameserve2.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.lanier.gameserve2.base.BaseListModel
import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.entity.Land
import org.lanier.gameserve2.entity.LandCropStageInfo
import org.lanier.gameserve2.entity.PropType
import org.lanier.gameserve2.entity.SeedStageInfo
import org.lanier.gameserve2.entity.dto.LandDto
import org.lanier.gameserve2.entity.dto.PetDto
import org.lanier.gameserve2.entity.dto.PlantLevelDto
import org.lanier.gameserve2.entity.dto.PlantParamDto
import org.lanier.gameserve2.service.BackpackService
import org.lanier.gameserve2.service.LandService
import org.lanier.gameserve2.service.PetService
import org.lanier.gameserve2.service.UserService
import org.lanier.gameserve2.utils.CommonUtil
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.*
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by 幻弦让叶
 * Date 2024/2/24 21:52
 */
@RestController
@RequestMapping("/land")
class LandController(
    private val landService: LandService,
    private val backpackService: BackpackService,
    private val petService: PetService,
) {

    @GetMapping("/get-info")
    fun getInfo(
        @RequestParam("petId") petId: String?
    ): BaseModel<BaseListModel<LandDto>> {
        if (petId.isNullOrEmpty()) {
            return BaseModel.notFoundUser()
        }
        val pid = CommonUtil.toInteger(petId)
        if (pid < 0) {
            return BaseModel.actionFailed()
        }
        return BaseModel.successList(16, landService.getLandInfosByPetId(pid))
    }

    /**
     * 此接口不应被主动调用
     *
     * @param userId
     * @return
     */
    @PostMapping("/new-created")
    @Synchronized
    fun newCreated(
        @RequestParam("petId") petId: String?
    ): BaseModel<Boolean> {
        if (petId.isNullOrEmpty()) {
            return BaseModel.notFoundUser()
        }
        val pid = CommonUtil.toInteger(petId)
        if (pid < 0) {
            return BaseModel.actionFailed()
        }
        val lands: MutableList<Land> = ArrayList()
        for (i in 0..15) {
            val land = Land(
                petId = pid,
                status = if (i <= 2) Land.IDLE else Land.UNLOCK
            )
            lands.add(land)
        }
        val effectRows = landService.newUserCreated(lands)
        return BaseModel.successBool("success")
    }

    /**
     * 该接口用于更新单个土地, 当需要[种植作物], [刷新土地信息]情况下需要调用
     * 每次需要传入参数必须包含 landId(操作的土地id), userId, seedId(作物种子id), bpkId(背包道具id)
     *
     * @param plantParamDto 种植参数
     * @return 是否成功
     * @Deprecated use [LandController.refresh] instead of this
     */
    @Deprecated("")
    @PostMapping("/update")
    fun update(
        @RequestBody plantParamDto: PlantParamDto
    ): BaseModel<Boolean> {
        if (!checkBackpack(plantParamDto.bpkId)) {
            return BaseModel.failedBool("数量不够哦~")
        }

        val curLandInfos = landService.getLandInfoByLid(plantParamDto.landId)
        if (curLandInfos.size != 1) {
            return BaseModel.failedBool("土地异常~")
        }
        // 当前土地信息
        val curLandInfo = curLandInfos[0]

        val seeds = landService.getSeedInfoById(plantParamDto.seedId)
        if (seeds.size != 1) {
            return BaseModel.failedBool("种子异常~")
        }
        // 需要种植的作物种子信息
        val wantPlantSeed = seeds[0]

        plantParamDto.maxHarvestCount = (wantPlantSeed.seedMaxHarvestCount)

        val objectMapper = ObjectMapper()
        try {
            // 获取作物阶段信息
            val seedStageInfo: SeedStageInfo =
                objectMapper.readValue(wantPlantSeed.stageInfo, SeedStageInfo::class.java)
            // 是否为新种植作物
            val isNewPlant = curLandInfo.seed == null
            // 当前阶段值
            var currentStage = if (isNewPlant) 0 else curLandInfo.currentStage
            val maxStage = seedStageInfo.maxStageSize()
            plantParamDto.maxStage = maxStage
            var nextStageStartTime = 0L
            var nextStageRemainTime = 0
            var canHarvest = false
            val curTime = System.currentTimeMillis()
            if (isNewPlant) { // 如果是新种植作物
                val singleInfo = seedStageInfo.getStageInfo(currentStage)
                nextStageStartTime = curTime + singleInfo.second * 1000L
                nextStageRemainTime = singleInfo.second
            } else {
                val interval: Long = curTime - curLandInfo.nextStageStartTime // 计算时间间隔
                if (interval < 0) { // 如果<0表示还在当前阶段
                    nextStageStartTime = curLandInfo.nextStageStartTime
                    val intervalS = (interval / 1000).toInt()
                    nextStageRemainTime = abs(intervalS.toDouble()).toInt() // 更新剩余时间
                } else { // 表示已经到下一阶段, 但是到哪一阶段不清楚
                    val calcStage = calculateStage(
                        seedStageInfo,
                        currentStage,
                        curLandInfo.nextStageStartTime,
                        curLandInfo.nextStageRemainTime
                    )
                    currentStage = calcStage.realCurrentStage
                    nextStageStartTime = calcStage.realNextStageStartTime
                    nextStageRemainTime = calcStage.realNextStageRemainTime
                    canHarvest = calcStage.canHarvest
                }
            }
            plantParamDto.status = (Land.PLANTING)
            plantParamDto.currentStage = (currentStage)
            plantParamDto.nextStageStartTime = (nextStageStartTime)
            plantParamDto.nextStageRemainTime = (nextStageRemainTime)
            val success = landService.updateLandInfo(plantParamDto)
            if (success) {
                if (isNewPlant) {
                    val consumeSuccess =
                        backpackService.consume(plantParamDto.petId, plantParamDto.seedId, PropType.SEED, 1)
                    return if (consumeSuccess) {
                        BaseModel.successBool("success")
                    } else {
                        BaseModel.failedBool("消耗失败")
                    }
                } else {
                    return BaseModel.successBool("success")
                }
            } else {
                return BaseModel.failedBool("更新失败")
            }
        } catch (e: JsonProcessingException) {
            return BaseModel.failedBool("系统异常~ " + e.message)
        }
    }

    /**
     * 该接口用于种植作物
     * @param plantParamDto
     * @return
     */
    @PostMapping("/plant")
    fun plant(
        @RequestBody plantParamDto: PlantParamDto
    ): BaseModel<Boolean> {
        if (!checkBackpack(plantParamDto.bpkId)) {
            return BaseModel.failedBool("数量不够哦~")
        }

        val curLandInfos = landService.getLandInfoByLid(plantParamDto.landId)
        if (curLandInfos.size != 1) {
            return BaseModel.failedBool("土地异常~")
        }
        // 当前土地信息
        val curLandInfo = curLandInfos[0]
        if (curLandInfo.seed != null) {
            return BaseModel.failedBool("已经有作物了哦~")
        }

        val seeds = landService.getSeedInfoById(plantParamDto.seedId)
        if (seeds.size != 1) {
            return BaseModel.failedBool("种子异常~")
        }
        // 需要种植的作物种子信息
        val wantPlantSeed = seeds[0]

        plantParamDto.maxHarvestCount = (wantPlantSeed.seedMaxHarvestCount)

        val objectMapper = ObjectMapper()
        try {
            // 获取作物阶段信息
            val seedStageInfo: SeedStageInfo =
                objectMapper.readValue(wantPlantSeed.stageInfo, SeedStageInfo::class.java)
            // 当前阶段值
            val currentStage = 0
            val maxStage = seedStageInfo.maxStageSize()
            plantParamDto.maxStage = maxStage
            var nextStageStartTime = 0L
            var nextStageRemainTime = 0
            val canHarvest = false
            val nextStageAllTime: String
            val singleInfo = seedStageInfo.getStageInfo(currentStage)
            val nextStageAllTimes: MutableList<Long> = ArrayList()
            val curTime = System.currentTimeMillis()
            nextStageStartTime = curTime + singleInfo.second * 1000L
            nextStageRemainTime = singleInfo.second
            var plantTime = curTime
            for (info in seedStageInfo.stageSustainTime) {
                val nextTime = plantTime + info * 1000L
                plantTime = nextTime
                nextStageAllTimes.add(nextTime)
            }
            val info = LandCropStageInfo(nextStageAllTimes)
            nextStageAllTime = objectMapper.writeValueAsString(info)
            plantParamDto.status = (Land.PLANTING)
            plantParamDto.currentStage = (currentStage)
            plantParamDto.nextStageStartTime = (nextStageStartTime)
            plantParamDto.nextStageRemainTime = (nextStageRemainTime)
            plantParamDto.nextStageAllTime = (nextStageAllTime)
            val success = landService.plantCrop(plantParamDto)
            if (success) {
                val consumeSuccess =
                    backpackService.consume(plantParamDto.petId, plantParamDto.seedId, PropType.SEED, 1)
                return if (consumeSuccess) {
                    BaseModel.successBool("success")
                } else {
                    BaseModel.failedBool("消耗失败")
                }
            } else {
                return BaseModel.failedBool("更新失败")
            }
        } catch (e: JsonProcessingException) {
            return BaseModel.failedBool("系统异常~ " + e.message)
        }
    }

    /**
     * 该接口用于更新单个土地信息
     * 参数必须包含 landId(操作的土地id)
     */
    @PostMapping("/refresh")
    fun refresh(
        @RequestParam("landId") landId: Int,
        @RequestParam("petId") petId: String?
    ): BaseModel<Boolean> {
        if (petId.isNullOrEmpty()) {
            return BaseModel.notFoundUser()
        }
        val pid = CommonUtil.toInteger(petId)
        if (pid < 0) {
            return BaseModel.actionFailed()
        }
        return refreshImpl(landId, pid, null)
    }

    /**
     * 收获单块土地
     * @param landId
     * @param userId
     * @return
     */
    @PostMapping("/harvest")
    fun harvest(
        @RequestParam("landId") landId: Int,
        @RequestParam("petId") petId: String?
    ): BaseModel<Boolean> {
        if (petId.isNullOrEmpty()) {
            return BaseModel.notFoundUser()
        }
        val pid = CommonUtil.toInteger(petId)
        if (pid < 0) {
            return BaseModel.actionFailed()
        }

        if (plantLevelDtos.isEmpty()) {
            val levelInfos: List<PlantLevelDto> = landService.levelInfo()
            if (levelInfos.isEmpty()) {
                return BaseModel.actionFailed()
            }
            plantLevelDtos.addAll(levelInfos)
        }
        val lands = landService.getLandInfoByLid(landId)
        if (lands.size != 1) {
            return BaseModel.failedBool("土地异常~")
        }

        val refreshResult = refreshImpl(landId, pid, lands[0])
        if (refreshResult.code == BaseModel.SUCCESS) {
            // 当前土地信息
            val curLandInfo = lands[0]
            if (curLandInfo.seed == null) {
                return BaseModel.successBool("没有作物~")
            }
            if (curLandInfo.currentStage !== curLandInfo.maxStage - 1) {
                return BaseModel.failedBool("还没有成熟哦~")
            }
            val curSeed = curLandInfo.seed
            val totalExpectExp = curSeed.totalHarvestExp // 收获作物理论上总的可以获得的经验值
            val totalRealExp = curSeed.harvestAmount * curSeed.harvestExp // 收获作物实际可以获得的经验值

            val petInfo: List<PetDto> = petService.getPetById(pid)
            if (petInfo.size != 1) {
                return BaseModel.notFoundUser()
            }
            val curUser: PetDto = petInfo[0]
            val curExp = curUser.currentPlantExp
            val curLevelInfo = curUser.currentLevel
            var realLevelId = curLevelInfo.plantLevel
            val realExp: Int = curExp + totalRealExp
            var lackExp = 0
            if (curLevelInfo.expRequired <= realExp) {
                for (level in plantLevelDtos) {
                    if (realExp < level.expRequired) {
                        realLevelId = level.plantLevel
                        lackExp = level.expRequired - realExp
                        break
                    }
                }
            }
            val uhResult: Boolean = petService.harvestCrop(realLevelId, totalRealExp, pid)
            if (uhResult) {
                val harvestResult = landService.harvestCrop(landId, pid)
                if (harvestResult) {
//                    ClientManager.sendMessage(message.toString())
                    return BaseModel.successBool("success")
                }
                return BaseModel.failedBool("收获失败了")
            }
            return BaseModel.failedBool("收获失败了")
        }
        return BaseModel.actionFailed()
    }

    private fun refreshImpl(
        landId: Int,
        pid: Int,
        @Nullable mLandDto: LandDto?
    ): BaseModel<Boolean> {
        val curLandInfo: LandDto
        if (mLandDto == null) {
            val curLandInfos = landService.getLandInfoByLid(landId)
            if (curLandInfos.size != 1) {
                return BaseModel.failedBool("土地异常~")
            }
            curLandInfo = curLandInfos[0]
        } else {
            curLandInfo = mLandDto
        }
        // 当前土地信息
        if (curLandInfo.seed == null) {
            return BaseModel.successBool("success")
        }
        val objectMapper = ObjectMapper()
        try {
            // 当前阶段值
            var currentStage: Int = curLandInfo.currentStage
            var nextStageStartTime = 0L
            var nextStageRemainTime = 0
            var canHarvest = false
            val nextStageAllTime: String = curLandInfo.nextStageAllTime
            val cropStageTimeInfo = objectMapper.readValue(nextStageAllTime, LandCropStageInfo::class.java)
            val calcStage = calculateStage(
                cropStageTimeInfo.nextStageAllTime,
                currentStage
            )
            currentStage = calcStage.realCurrentStage
            nextStageStartTime = calcStage.realNextStageStartTime
            nextStageRemainTime = calcStage.realNextStageRemainTime
            canHarvest = calcStage.canHarvest
            val plantParamDto = PlantParamDto(
                petId = pid,
                landId = landId,
                status = Land.PLANTING,
                currentStage = currentStage,
                nextStageStartTime = nextStageStartTime,
                nextStageRemainTime = nextStageRemainTime,
                nextStageAllTime = nextStageAllTime
            )
            val success = landService.updateLandInfo(plantParamDto)
            return if (success) {
                BaseModel.successBool("success")
            } else {
                BaseModel.failedBool("更新失败")
            }
        } catch (e: JsonProcessingException) {
            return BaseModel.failedBool("系统异常~ " + e.message)
        }
    }

    /**
     * 计算当前时间对应的阶段和时间
     *
     * @param allNextStageTime 下个阶段开始时间节点
     * @param currentStage     当前阶段index
     * @return 实际的阶段, 实际的开始时间, 实际的剩余时间, 是否可以收获
     */
    private fun calculateStage(
        allNextStageTime: List<Long>,
        currentStage: Int
    ): CalcStage {
        var canHarvest = false // 已经过了最终阶段, 可以收获了
        val curTime = System.currentTimeMillis() // 获取当前时间
        var realCurrentStage = 0
        var realNextStageStartTime = 0L
        var realNextStageRemainTime = 0
        val lastStageTime = allNextStageTime[allNextStageTime.size - 1]
        if (curTime < lastStageTime) { // 说明还未处于可收获阶段
            for (i in currentStage until allNextStageTime.size) {
                val nextStageTime = allNextStageTime[i] // 下一阶段的开始时间戳
                if (curTime < nextStageTime) { // 就处于当前阶段
                    realCurrentStage = i
                    realNextStageStartTime = nextStageTime
                    val remainTimeToNextStage = abs(((curTime - nextStageTime) / 1000).toDouble())
                        .toInt() // 当前时间到下一阶段的剩余秒数
                    realNextStageRemainTime = remainTimeToNextStage
                    break
                }
            }
        } else {
            canHarvest = true
            realCurrentStage = allNextStageTime.size - 1
            realNextStageStartTime = 0
            realNextStageRemainTime = 0
        }
        val calcStage = CalcStage()
        calcStage.realCurrentStage = realCurrentStage
        calcStage.realNextStageStartTime = realNextStageStartTime
        calcStage.realNextStageRemainTime = realNextStageRemainTime
        calcStage.canHarvest = canHarvest
        return calcStage
    }

    /**
     * 计算当前时间对应的阶段和时间
     *
     * @param seedStageInfos      阶段list
     * @param currentStage        当前阶段index
     * @param nextStageStartTime  下一阶段的开始日期
     * @param nextStageRemainTime 距离下一阶段还有多长时间
     * @return 实际的阶段, 实际的开始时间, 实际的剩余时间, 是否可以收获
     */
    private fun calculateStage(
        seedStageInfos: SeedStageInfo,
        currentStage: Int,
        nextStageStartTime: Long,
        nextStageRemainTime: Int
    ): CalcStage {
        // 走到这边已经说明在下一阶段了
        var canHarvest = false // 已经过了最终阶段, 可以收获了
        var realStage = -1
        var realRemainTime = 0
        var realStartTime = nextStageStartTime // 下一阶段开始时间
        val curTime = System.currentTimeMillis() // 重新获取时间戳
        val intervalS = ((curTime - nextStageStartTime) / 1000).toInt() // 总的实际时间差
        val i1 = intervalS - nextStageRemainTime // 上次记录的剩余时间, 计算到下一阶段的时间
        realRemainTime = i1
        if (currentStage + 1 < seedStageInfos.maxStageSize()) { // 如果下一阶段不是最后阶段
            var existStage = false
            for (i in currentStage + 1 until seedStageInfos.maxStageSize()) {
                val curStageTime = seedStageInfos.getStageInfo(i).second
                realRemainTime -= curStageTime // 判断与当前阶段的时间差
                realStage = i
                realStartTime += curStageTime * 1000L
                if (realRemainTime < 0) { // 表示实际为当前阶段
                    existStage = true
                    realRemainTime = abs(realRemainTime.toDouble()).toInt()
                    break
                }
            }
            if (!existStage) { // 表示已经超过最大阶段时间了
                realRemainTime = 0
                realStartTime = 0
                canHarvest = true
            }
        } else {
            // 否则说明下一阶段已经是最后阶段
            realStage = seedStageInfos.maxStageSize() - 1
            //计算最后阶段的剩余时间
            val i2 = seedStageInfos.getStageInfo(seedStageInfos.maxStageSize() - 1).second - i1
            realStartTime =
                if (i2 < 0) 0 else nextStageStartTime + seedStageInfos.getStageInfo(seedStageInfos.maxStageSize() - 1).second * 1000L
            realRemainTime = max(i2.toDouble(), 0.0).toInt()
            canHarvest = i2 < 0
        }
        val calcStage = CalcStage()
        calcStage.realCurrentStage = realStage
        calcStage.realNextStageStartTime = realStartTime
        calcStage.realNextStageRemainTime = realRemainTime
        calcStage.canHarvest = canHarvest
        return calcStage
    }

    private fun checkBackpack(bpkId: Int): Boolean {
        val quality = backpackService!!.getQualityById(bpkId)
        return quality != null && quality > 0
    }

    private class CalcStage {
        /**
         * 真实的阶段
         */
        var realCurrentStage: Int = 0

        /**
         * 真实的下阶段开始时间
         */
        var realNextStageStartTime: Long = 0

        /**
         * 真实的距离下阶段的剩余时间
         */
        var realNextStageRemainTime: Int = 0

        /**
         * 是否可以直接收获
         */
        var canHarvest: Boolean = false
    }

    companion object {
        private val plantLevelDtos = mutableListOf<PlantLevelDto>()
    }
}
