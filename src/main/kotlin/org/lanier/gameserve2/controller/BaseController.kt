package org.lanier.gameserve2.controller

/**
 * Created by 幻弦让叶
 * Date 2024/2/24 17:54
 */
abstract class BaseController {
    /**
     * 处理列表类型数据
     * @param page 页数
     * @param pageSize 每页返回数据量
     * @return f: 偏移, s: 数量
     */
    protected fun handlePage(
        page: Int,
        pageSize: Int
    ): Pair<Int, Int> {
        val mPage = if (page <= 0) 1 else page
        val offset = (mPage - 1) * pageSize
        return Pair(offset, pageSize)
    }

    /**
     * 处理列表类型数据
     * @param page 页数
     * @param pageSize 每页返回数据量
     * @return 偏移
     */
    protected fun handleOffset(
        page: Int,
        pageSize: Int
    ): Int {
        val mPage = if (page <= 0) 1 else page
        return (mPage - 1) * pageSize
    }
}
