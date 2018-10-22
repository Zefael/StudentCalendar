package com.blueamber.studentcalendar.domain.usecases

import android.util.Log
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.EventDto
import com.blueamber.studentcalendar.models.Day
import com.blueamber.studentcalendar.models.Work
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CelcatUseCase(private val remote: NetworkXmlRepository, private val local: DayDao) {

    suspend fun downloadCelcat(): Boolean {
        var result: Boolean
        try {
            val request = remote.getCelcat()
            val response = request.getCompleted()
            result = request.isCompleted
            if (result) {
                sortAndInsertInBase(response.body() ?: CelcatXmlDto())
            }
        } catch (exception: Exception) {
            Log.d(CelcatUseCase::class.java.simpleName, "download Celcat xml file and insert in database", exception)
            result = false
        }
        return result
    }

    private fun sortAndInsertInBase(celcatXmlDto: CelcatXmlDto) {
        val sortedCelcat = celcatXmlDto.event.sortedWith(compareBy { it.date })
        var date = sortedCelcat.get(0).date
        var works: ArrayList<Work> = ArrayList()

        for (event in sortedCelcat) {
            val work = buildWork(event)
            if (date.equals(event.date)) {
                works.add(work)
            } else {
                local.insert(Day(formatDate(date), works))
                date = event.date
                works = ArrayList()
            }
        }
    }

    private fun buildWork(eventDto: EventDto): Work {
        return Work(
            buildListItemToString(eventDto.resources?.modules?.items ?: emptyList()),
            eventDto.category, eventDto.startTime,
            eventDto.endTime,
            buildListItemToString(eventDto.resources?.staffs?.items ?: emptyList()),
            buildListItemToString(eventDto.resources?.rooms?.items ?: emptyList()),
            buildListItemToString(eventDto.resources?.groups?.items ?: emptyList()),
            eventDto.notes
        )
    }

    private fun buildListItemToString(items: List<String>): String {
        var result = ""
        for (item in items) {
            result += item + "\n"
        }
        return result
    }

    private fun formatDate(date: String): Date {
        return try {
            SimpleDateFormat("dd MMM yyyy", Locale.FRANCE).parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }
}