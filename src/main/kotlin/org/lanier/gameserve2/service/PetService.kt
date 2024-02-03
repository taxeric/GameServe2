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

    fun updateStatusOfSatiety(petId: Int, newValue: Int) = mapper.updateStatusOfSatiety(petId, newValue)

    fun updateStatusOfCleanliness(petId: Int, newValue: Int) = mapper.updateStatusOfCleanliness(petId, newValue)
}