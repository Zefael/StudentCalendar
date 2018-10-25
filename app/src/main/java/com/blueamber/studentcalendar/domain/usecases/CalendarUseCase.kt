package com.blueamber.studentcalendar.domain.usecases

import android.util.Log
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.enterprise.baseproject.util.DateUtil

class CalendarUseCase(private val remote: NetworkJsonRepository, private val local: DayDao) {

    fun downloadJsonCalendar(): Boolean {
        var result: Boolean
        try {
            val request = remote.getCalendar()
            val response = request.getCompleted()
            result = request.isCompleted
            if (result) {
                sortAndInsertInBase(response.body() ?: emptyMap())
            }
        } catch (exception: Exception) {
            Log.e(CalendarUseCase::class.java.simpleName, "Failed: download calendar json file and insert in database", exception)
            result = false
        }
        return result
    }

    private fun sortAndInsertInBase(data: Map<String, CalendarJsonDto>) {
        // TODO : need to sort my data before map
        data.forEach{ (_, item) ->
            val date = DateUtil.formatDate(item.date_start ?: "")

        }
    }
}