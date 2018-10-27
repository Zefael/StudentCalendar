package com.blueamber.studentcalendar.domain.usecases

import android.util.Log
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.models.Day
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.models.Work
import com.blueamber.studentcalendar.tools.DateUtil
import java.util.*

class CalendarUseCase(private val remote: NetworkJsonRepository, private val locale: DayDao) {

    suspend fun downloadJsonCalendar(): List<Day> {
        return try {
            val request = remote.getCalendar()
            val response = request.await()
            if (request.isCompleted) {
                convert(response.body() ?: emptyMap())
            } else emptyList()
        } catch (exception: Exception) {
            Log.e(CalendarUseCase::class.java.simpleName, "Failed: download calendar json file and insert in database", exception)
            emptyList()
        }
    }

    private fun convert(data: Map<String, CalendarJsonDto>) : List<Day> {
        val result = ArrayList<Day>()
        var date = DateUtil.formatDateDashT(data.values.elementAt(0).date_start)
        var works: ArrayList<Work> = ArrayList()

        data.forEach{ (_, item) ->
            val work = buildWork(item)
            val dateNext = DateUtil.formatDateDashT(item.date_start)
            if (date == dateNext) {
                works.add(work)
            } else {
                result.add(Day(date, works))
                date = dateNext
                works = ArrayList()
            }
        }
        return result
    }

    private fun buildWork(calendarJsonDto: CalendarJsonDto): Work {
        return Work(
            calendarJsonDto.acronym.substringAfter("::"),
            calendarJsonDto.type,
            TypeOfSource.OTHER,
            calendarJsonDto.date_start.substringAfter("T"),
            calendarJsonDto.date_end.substringAfter("T"),
            calendarJsonDto.lecturer,
            calendarJsonDto.location.substringAfter("::"),
            calendarJsonDto.group,
            if (calendarJsonDto.comment.contains("Imported"))  "" else (calendarJsonDto.comment)
        )
    }
}