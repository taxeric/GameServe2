package org.lanier.gameserve2.service

import org.lanier.gameserve2.entity.Pet
import org.lanier.gameserve2.mapper.PetMapper
import org.springframework.stereotype.Service

@Service
class PetService(
    private val mapper: PetMapper
) {

    fun create(pet: Pet) = mapper.create(pet)

    fun getPetsByUserId(userId: Int) = mapper.getPetsByUserId(userId)

    fun getPetById(petId: Int) = mapper.getPetById(petId)

    fun getPlantInfoById(petId: Int) = mapper.getPlantInfoById(petId)

    fun updateStatusOfSatiety(petId: Int, newValue: Int) = mapper.updateStatusOfSatiety(petId, newValue)

    fun updateStatusOfCleanliness(petId: Int, newValue: Int) = mapper.updateStatusOfCleanliness(petId, newValue)

    fun updateStatusOfHealthy(petId: Int, newValue: Int) = mapper.updateStatusOfHealthy(petId, newValue)

    fun getPetCoin(petId: Int): Int {
        return mapper.getPetCoin(petId)
    }

    fun consumeCoin(coin: Int, petId: Int): Boolean {
        return mapper.consumeCoin(coin, petId) == 1
    }

    fun addCoin(coin: Int, petId: Int): Boolean {
        return mapper.addCoin(coin, petId) == 1
    }

    fun harvestCrop(plantLevelId: Int, cropTotalExp: Int, petId: Int): Boolean {
        return mapper.harvestCrop(plantLevelId, cropTotalExp, petId) == 1
    }
}