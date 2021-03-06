package com.blueamber.studentcalendar.tools

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    const val PATTERN_DATE = "dd-MM-yyyy"

    fun yesterday(): Date {
        val result = Calendar.getInstance()
        result.add(Calendar.DAY_OF_MONTH, -1)
        return result.time
    }

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

    fun isNowBeforeToTasks(hours: String): Boolean {
        val todayDate = Calendar.getInstance()

        val hoursToInt = hours.substringBefore(":").toInt()
        val minuteToInt = hours.substringAfter(":").toInt()
        return (todayDate.get(Calendar.HOUR_OF_DAY) < hoursToInt || (todayDate.get(Calendar.HOUR_OF_DAY) == hoursToInt && todayDate.get(Calendar.MINUTE) < minuteToInt))
    }

    fun dateAddDay(date: Long, day: Int): Date {
        val c1 = Calendar.getInstance()
        c1.timeInMillis = date
        c1.add(Calendar.DAY_OF_YEAR, day)

        return c1.time
    }

    fun isEqualsDate(d1: Long, d2: Long): Boolean {
        val c1 = Calendar.getInstance()
        c1.timeInMillis = d1

        val c2 = Calendar.getInstance()
        c2.timeInMillis = d2

        return (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
    }

    fun monthOfDate(date: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        return when (calendar.get(Calendar.MONTH)) {
            Calendar.JANUARY -> "Janvier"
            Calendar.FEBRUARY -> "Février"
            Calendar.MARCH -> "Mars"
            Calendar.APRIL -> "Avril"
            Calendar.MAY -> "Mai"
            Calendar.JUNE -> "Juin"
            Calendar.JULY -> "Juillet"
            Calendar.AUGUST -> "Août"
            Calendar.SEPTEMBER -> "Septembre"
            Calendar.OCTOBER -> "Octobre"
            Calendar.NOVEMBER -> "Novembre"
            Calendar.DECEMBER -> "Décembre"
            else -> ""
        }
    }

    fun dayOfWeek(date: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "lun"
            Calendar.TUESDAY -> "mar"
            Calendar.WEDNESDAY -> "mer"
            Calendar.THURSDAY -> "jeu"
            Calendar.FRIDAY -> "ven"
            Calendar.SATURDAY -> "sam"
            Calendar.SUNDAY -> "dim"
            else -> ""
        }
    }

    fun dayOfMonth(date: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun formatDateSlash(date: String): Date {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
            val dateFormat = sdf.parse(date)
            sdf.applyPattern(PATTERN_DATE)
            sdf.parse(sdf.format(dateFormat))
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }

    fun formatDateDashT(date: String): Date {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.FRANCE)
            val dateFormat = sdf.parse(date)
            sdf.applyPattern(PATTERN_DATE)
            sdf.parse(sdf.format(dateFormat))
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }

    fun addDayToDateString(date: String, day: Int): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
            val c = Calendar.getInstance()
            c.time = sdf.parse(date)
            c.add(Calendar.DATE, day)
            sdf.format(c.time)
        } catch (pE: ParseException) {
            pE.printStackTrace()
            date
        }
    }

    fun calendarToString(date: Long): String {
        return try {
            val cal = Calendar.getInstance()
            cal.timeInMillis = date
            val sdf = SimpleDateFormat("EE dd MMM yyyy à HH:mm", Locale.FRANCE)
            sdf.format(cal.time)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
}