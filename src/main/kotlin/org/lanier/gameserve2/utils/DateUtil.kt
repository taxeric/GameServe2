package org.lanier.gameserve2.utils

import java.util.*

/**
 * Created by 黄瓜
 * Date 2024/2/11 15:20
 */
object DateUtil {

    fun isCurrentYear(year: Int) : Boolean {
        val curYear = Calendar.getInstance().get(Calendar.YEAR)
        return year == curYear
    }

    fun isCurrentMonth(month: Int) : Boolean {
        val curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        return month == curMonth
    }

    fun isCurrentDay(day: Int) : Boolean {
        val curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        return day == curDay
    }
}