package org.lanier.gameserve2.service

import org.lanier.gameserve2.entity.Pet
import org.lanier.gameserve2.mapper.PetMapper
import org.springframework.stereotype.Service

@Service
class PetService(
    private val mapper: PetMapper
) {

    fun create(pet: Pet) = mapper.create(pet)

    fun getPetById(petId: Int) = mapper.getPetById(petId)
}