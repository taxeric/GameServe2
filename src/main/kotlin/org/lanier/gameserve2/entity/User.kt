package org.lanier.gameserve2.entity

import com.fasterxml.jackson.annotation.JsonIgnore

data class User(
    val userId: Int = 0,
    val account: String = "",
    @JsonIgnore val password: String = "",
)
