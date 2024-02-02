package org.lanier.gameserve2.base

data class BaseModel<T>(
    val code: Int,
    val message: String,
    val serviceTime: Long,
    val data: T?,
) {

    companion object {

        const val RES_SUCCESS = 0
        const val RES_ERROR = -1

        fun <T> success(data: T? = null, message: String = "success") = BaseModel(
            message = message,
            code = RES_SUCCESS,
            serviceTime = System.currentTimeMillis(),
            data = data,
        )

        fun <T> failure(
            code: Int = RES_ERROR,
            message: String = "failed",
            data: T? = null,
        ) = BaseModel(
            code = code,
            message = message,
            serviceTime = System.currentTimeMillis(),
            data = data
        )

        fun failureBoolean(
            code: Int = RES_ERROR,
            message: String = "failed",
        ) = BaseModel(
            code = code,
            message = message,
            serviceTime = System.currentTimeMillis(),
            data = false
        )
    }
}
