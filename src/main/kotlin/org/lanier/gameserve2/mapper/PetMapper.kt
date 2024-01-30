package org.lanier.gameserve2.mapper

import org.apache.ibatis.annotations.Mapper
import org.lanier.gameserve2.entity.Pet

@Mapper
interface PetMapper {

    fun create(pet: Pet) : Int

    fun getPetById(petId: Int) : List<Pet>
}