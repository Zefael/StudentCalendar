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

    fun monthOfDate(date: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        when(calendar.get(Calendar.MONTH)) {
            Calendar.JANUARY -> return "Janvier"
            Calendar.FEBRUARY -> return "Février"
            Calendar.MARCH -> return "Mars"
            Calendar.APRIL -> return "Avril"
            Calendar.MAY -> return "Mai"
            Calendar.JUNE -> return "Juin"
            Calendar.JULY -> return "Juillet"
            Calendar.AUGUST -> return "Août"
            Calendar.SEPTEMBER -> return "Septembre"
            Calendar.OCTOBER -> return "Octobre"
            Calendar.NOVEMBER -> return "Novembre"
            Calendar.DECEMBER -> return "Décembre"
            else -> return ""
        }
    }

    fun dayOfWeek(date: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        when(calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> return "lun."
            Calendar.TUESDAY -> return "mar."
            Calendar.WEDNESDAY -> return "mer."
            Calendar.THURSDAY -> return "jeu."
            Calendar.FRIDAY -> return "ven."
            Calendar.SATURDAY -> return "sam."
            Calendar.SUNDAY -> return "dim."
            else -> return ""
        }
    }

    fun dayOfMonth(date: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        return calendar.get(Calendar.DAY_OF_MONTH)
    }
}