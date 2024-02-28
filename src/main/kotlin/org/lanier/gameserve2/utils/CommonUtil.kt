package org.lanier.gameserve2.utils

/**
 * Created by 幻弦让叶
 * Date 2024/2/28 22:13
 */
object CommonUtil {

    @JvmStatic
    fun toIntegerOrDef(s: String, def: Int): Int {
        val m = try {
            s.toInt()
        } catch (e: Exception) {
            def
        }
        return m
    }

    @JvmStatic
    fun toInteger(s: String?): Int {
        s?.let {
            return toIntegerOrDef(s, -1)
        } ?: return -1
    }
}