package com.enterprise.baseproject.util

import java.util.*

object DateUtil {

    fun isToday(date: Long): Boolean {
        val currentDate = Calendar.getInstance()
        currentDate.timeInMillis = date

        val todayDate = Calendar.getInstance()

        return todayDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                todayDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)
    }

    fun isYesterday(date: Long): Boolean {
        val currentDate = Calendar.getInstance()
        currentDate.timeInMillis = date

        val yesterdayDate = Calendar.getInstance()
        yesterdayDate.add(Calendar.DATE, -1)

        return yesterdayDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                yesterdayDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)
    }

    fun daysBetween(d1: Long, d2: Long): Int {
        val c1 = Calendar.getInstance()
        c1.timeInMillis = d1

        val c2 = Calendar.getInstance()
        c2.timeInMillis = d2

        return c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR)
    }

}