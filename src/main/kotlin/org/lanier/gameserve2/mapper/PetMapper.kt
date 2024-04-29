package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.Pet
import org.lanier.gameserve2.entity.dto.PetDto

@Mapper
interface PetMapper {

    fun create(pet: Pet) : Int

    fun getPetsByUserId(userId: Int) : List<PetDto>

    fun getPetById(petId: Int) : List<PetDto>

    fun getPlantInfoById(petId: Int) : PetDto

    fun updateStatusOfSatiety(petId: Int, newValue: Int) : Int

    fun updateStatusOfCleanliness(petId: Int, newValue: Int) : Int

    fun updateStatusOfHealthy(petId: Int, newValue: Int) : Int

    fun getPetCoin(petId: Int): Int

    fun consumeCoin(coin: Int, petId: Int): Int
    fun addCoin(coin: Int, petId: Int): Int

    fun harvestCrop(plantLevelId: Int?, cropTotalExp: Int, petId: Int): Int
}