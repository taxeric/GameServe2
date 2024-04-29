package org.lanier.gameserve2.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.lanier.gameserve2.entity.dto.PetDto

data class User(
    val userId: Int = 0,
    val account: String = "",
    @JsonIgnore val password: String = "",
    val pets: List<PetDto> = emptyList()
)
