package com.blueamber.studentcalendar.domain.usecases

import android.util.Log
import com.blueamber.studentcalendar.domain.local.TastsCalendarDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.tools.DateUtil

class CalendarUseCase(private val remote: NetworkJsonRepository, private val locale: TastsCalendarDao) {

    suspend fun downloadJsonCalendar(): List<TasksCalendar> {
        return try {
            val request = remote.getCalendar()
            val response = request.await()
            if (request.isCompleted) {
                convert(response.body() ?: emptyMap())
            } else emptyList()
        } catch (exception: Exception) {
            Log.e(
                CalendarUseCase::class.java.simpleName,
                "Failed: download calendar json file and insert in database",
                exception
            )
            emptyList()
        }
    }

    private fun convert(data: Map<String, CalendarJsonDto>): List<TasksCalendar> {
        val result = ArrayList<TasksCalendar>()

        data.forEach { (_, item) ->
            result.add(
                TasksCalendar(
                    DateUtil.formatDateDashT(item.date_start),
                    item.acronym.substringAfter("::"),
                    item.type,
                    TypeOfSource.OTHER,
                    item.date_start.substringAfter("T"),
                    item.date_end.substringAfter("T"),
                    item.lecturer,
                    item.location.substringAfter("::"),
                    item.group,
                    if (item.comment.contains("Imported")) "" else (item.comment)
                )
            )
        }
        return result
    }
}