package com.blueamber.studentcalendar.domain.usecases

import android.content.Context
import android.util.Log
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.EventDto
import com.blueamber.studentcalendar.models.Day
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.models.Work
import com.blueamber.studentcalendar.tools.DateUtil
import com.blueamber.studentcalendar.tools.FileUtil
import org.simpleframework.xml.core.Persister

class CelcatUseCase(private val remote: NetworkXmlRepository, private val local: DayDao) {

    suspend fun downloadCelcat(context: Context): List<Day> {
        return try {
            val request = remote.getCelcat()
            val response = request.await()
            if (request.isCompleted) {
                val serializer = Persister()
                val source = FileUtil.writeResponseBodyToDisk(context, response.body(), "calendrier_semestre.xml")
                val semestre = serializer.read<CelcatXmlDto>(CelcatXmlDto::class.java, source)
                convert(semestre)
            } else emptyList()
        } catch (exception: Exception) {
            Log.e(
                CelcatUseCase::class.java.simpleName,
                "Failed : download Celcat xml file and insert in database",
                exception
            )
            emptyList()
        }
    }

    private fun convert(celcatXmlDto: CelcatXmlDto): List<Day> {
        val result = ArrayList<Day>()
        val sortedCelcat = celcatXmlDto.event.sortedWith(compareBy { it.date })
        var date = sortedCelcat[0].date
        var works: ArrayList<Work> = ArrayList()

        for (event in sortedCelcat) {
            val work = buildWork(event)
            if (date == event.date) {
                works.add(work)
            } else {
                result.add(Day(DateUtil.formatDateSlash(date), works))
                date = event.date
                works = ArrayList()
            }
        }
        return result
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