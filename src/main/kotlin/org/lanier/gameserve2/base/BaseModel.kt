package org.lanier.gameserve2.base

data class BaseModel<T>(
    val code: Int = RES_SUCCESS,
    val msg: String = "",
    val data: T? = null
) {

    companion object {

        const val RES_SUCCESS = 200
        const val RES_ERROR = 201
    }
}
