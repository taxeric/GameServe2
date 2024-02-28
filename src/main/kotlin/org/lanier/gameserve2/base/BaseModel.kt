package org.lanier.gameserve2.base

class BaseModel<D> {
    var code: String = ""
    var message: String = ""
    var serverTime: Long = 0L
    var data: D? = null

    constructor(code: String, message: String, serverTime: Long, data: D)
    constructor()

    fun isSuccess(): Boolean {
        return code == BaseModel.SUCCESS
    }

    companion object {

        const val SUCCESS: String = "000000"
        const val FAILED: String = "100001"
        const val FAILED_NOT_FOUND_USER: String = "000001"
        const val FAILED_UNKNOWN: String = "010000"
        const val FAILED_ACTION: String = "0200000"

        const val RES_SUCCESS = 0
        const val RES_ERROR = -1

        fun <T> success(data: T, message: String = "success") = BaseModel(
            message = message,
            code = SUCCESS,
            serverTime = System.currentTimeMillis(),
            data = data,
        )

        fun <T> failure(
            code: String = FAILED,
            message: String = "failed",
            data: T? = null,
        ) = BaseModel(
            code = code,
            message = message,
            serverTime = System.currentTimeMillis(),
            data = data
        )

        fun failureBoolean(
            code: String = FAILED,
            message: String = "failed",
        ) = BaseModel(
            code = code,
            message = message,
            serverTime = System.currentTimeMillis(),
            data = false
        )

        fun <D> successList(
            total: Int,
            d: List<D>
        ): BaseModel<BaseListModel<D>> {
            val m: BaseModel<BaseListModel<D>> = successModel<BaseListModel<D>>()
            val listData: BaseListModel<D> = BaseListModel()
            listData.total = total
            listData.list = d
            m.data = listData
            return m
        }

        fun <D> failed(
            code: String,
            message: String
        ): BaseModel<D> {
            val m = failedModel<D>(code)
            m.message = message
            m.data = null
            return m
        }

        fun successBool(
            message: String
        ): BaseModel<Boolean> {
            val m = successModel<Boolean>()
            m.message = message
            m.data = true
            return m
        }

        fun failedBool(
            message: String
        ): BaseModel<Boolean> {
            val m = failedModel<Boolean>(BaseModel.FAILED)
            m.message = message
            m.data = false
            return m
        }

        fun <D> notFoundUser(): BaseModel<D> {
            val m = failedModel<D>(BaseModel.FAILED_NOT_FOUND_USER)
            m.message = "没有找到用户哦"
            return m
        }

        fun <D> actionFailed(): BaseModel<D> {
            val m = failedModel<D>(BaseModel.FAILED_ACTION)
            m.message = "操作失败了..."
            return m
        }

        fun <D> unknownErr(): BaseModel<D> {
            val m = failedModel<D>(BaseModel.FAILED_UNKNOWN)
            m.message = "发生未知异常了..."
            return m
        }

        private fun <D> successModel(): BaseModel<D> {
            val m = serverTimeModel<D>()
            m.message = "success"
            m.code = BaseModel.SUCCESS
            return m
        }

        private fun <D> failedModel(code: String): BaseModel<D> {
            val m = serverTimeModel<D>()
            m.code = code
            return m
        }

        private fun <D> serverTimeModel(): BaseModel<D> {
            val m: BaseModel<D> = BaseModel<D>()
            m.serverTime = System.currentTimeMillis()
            return m
        }
    }
}
