package com.blueamber.studentcalendar.domain.usecases

import android.util.Log
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.EventDto
import com.blueamber.studentcalendar.models.Day
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.models.Work
import com.enterprise.baseproject.util.DateUtil

class CelcatUseCase(private val remote: NetworkXmlRepository, private val local: DayDao) {

    fun downloadCelcat(): Boolean {
        var result: Boolean
        try {
            val request = remote.getCelcat()
            val response = request.getCompleted()
            result = request.isCompleted
            if (result) {
                sortAndInsertInBase(response.body() ?: CelcatXmlDto())
            }
        } catch (exception: Exception) {
            Log.e(
                CelcatUseCase::class.java.simpleName,
                "Failed : download Celcat xml file and insert in database",
                exception
            )
            result = false
        }
        return result
    }

    private fun sortAndInsertInBase(celcatXmlDto: CelcatXmlDto) {
        val sortedCelcat = celcatXmlDto.event.sortedWith(compareBy { it.date })
        var date = sortedCelcat[0].date
        var works: ArrayList<Work> = ArrayList()

        for (event in sortedCelcat) {
            val work = buildWork(event)
            if (date == event.date) {
                works.add(work)
            } else {
                local.insert(Day(DateUtil.formatDate(date), works))
                date = event.date
                works = ArrayList()
            }
        }
    }

    private fun buildWork(eventDto: EventDto): Work {
        return Work(
            buildListItemToString(eventDto.resources.modules),
            eventDto.category,
            TypeOfSource.CELCAT,
            eventDto.startTime,
            eventDto.endTime,
            buildListItemToString(eventDto.resources.staffs),
            buildListItemToString(eventDto.resources.rooms),
            buildListItemToString(eventDto.resources.groups),
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
}