package org.lanier.gameserve2.base

data class BaseModel(
    val code: Int = RES_SUCCESS,
    val msg: String = "",
) {

    companion object {

        const val RES_SUCCESS = 200
        const val RES_ERROR = 201
    }
}
