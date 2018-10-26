package com.blueamber.studentcalendar.domain.usecases

import android.util.Log
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.models.Day
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.models.Work
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
        val dataSorted = data.toList().sortedBy { (_, value) -> DateUtil.formatDate(value.date_start) }.toMap()
        var date = dataSorted.values.elementAt(0).date_start
        var works: ArrayList<Work> = ArrayList()
        dataSorted.forEach{ (_, item) ->
            val work = buildWork(item)
            if (date == item.date_start) {
                works.add(work)
            } else {
                local.insert(Day(DateUtil.formatDate(date), works))
                date = item.date_start
                works = ArrayList()
            }
        }
    }

    private fun buildWork(calendarJsonDto: CalendarJsonDto): Work {
        return Work(
            calendarJsonDto.acronym.substring("::".indices),
            calendarJsonDto.type,
            TypeOfSource.OTHER,
            calendarJsonDto.date_start.substring("T".indices),
            calendarJsonDto.date_end.substring("T".indices),
            calendarJsonDto.lecturer,
            calendarJsonDto.location.substring("::".indices),
            calendarJsonDto.group,
            if (calendarJsonDto.comment.contains("Imported"))  "" else (calendarJsonDto.comment)
        )
    }
}