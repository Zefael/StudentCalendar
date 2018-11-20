package com.blueamber.studentcalendar.domain.usecases

import android.content.Context
import android.util.Log
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.tools.ColorUtil
import com.blueamber.studentcalendar.tools.DateUtil
import com.blueamber.studentcalendar.tools.FileUtil
import org.simpleframework.xml.core.Persister

class CelcatUseCase(private val remote: NetworkXmlRepository, private val local: GroupsDao) {

    suspend fun downloadCelcat(context: Context): List<TasksCalendar> {
        return try {
            val request = remote.getCelcat()
            val response = request.await()
            if (request.isCompleted) {
                val serializer = Persister()
                val source = FileUtil.writeResponseBodyToDisk(context, response.body(), "calendrier_semestre.xml")
                val semestre = serializer.read<CelcatXmlDto>(CelcatXmlDto::class.java, source)
                convert(semestre, local.getGroups())
            } else emptyList()
        } catch (exception: Exception) {
            Log.e(
                CelcatUseCase::class.java.simpleName,
                "Failed : download Celcat xml file and insert in database", exception
            )
            emptyList()
        }
    }

    private fun convert(celcatXmlDto: CelcatXmlDto, groups: List<Groups>): List<TasksCalendar> {
        val result = ArrayList<TasksCalendar>()
        val sortedCelcat = celcatXmlDto.event.sortedWith(compareBy { it.date })

        for (event in sortedCelcat) {
            val groupBuilded = buildListItemToString(event.resources.groups)
            val group = groups.find { it.originalGroups == groupBuilded }
            if (group?.visibility ?: true) {
                result.add(
                    TasksCalendar(
                        DateUtil.formatDateSlash(DateUtil.addDayToDateString(event.date, event.day)),
                        buildListItemToString(event.resources.modules),
                        event.category,
                        TypeOfSource.CELCAT,
                        ColorUtil.colorForTask(event.category, TypeOfSource.CELCAT),
                        event.startTime,
                        event.endTime,
                        buildListItemToString(event.resources.staffs),
                        buildListItemToString(event.resources.rooms),
                        group?.newGroups ?: groupBuilded,
                        event.notes
                    )
                )
            }
        }
        return result
    }

    private fun buildListItemToString(items: List<String>): String {
        var result = ""
        val it = items.iterator()
        while (it.hasNext()) {
            val item = it.next()
            result += item
            if (it.hasNext()) {
                result += "\n"
            }
        }
        return result
    }
}