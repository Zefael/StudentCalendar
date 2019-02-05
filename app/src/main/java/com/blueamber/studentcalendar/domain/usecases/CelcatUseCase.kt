package com.blueamber.studentcalendar.domain.usecases

import android.content.Context
import android.util.Log
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.PrimaryGroupsDao
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.EventDto
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.PrimaryGroups
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.tools.ColorUtil
import com.blueamber.studentcalendar.tools.DateUtil
import com.blueamber.studentcalendar.tools.FileUtil
import org.simpleframework.xml.core.Persister
import java.util.*

class CelcatUseCase(private val remote: NetworkXmlRepository, private val local: GroupsDao,
                    private  val localPrimaryGroup: PrimaryGroupsDao) {

    suspend fun downloadCelcat(context: Context): List<TasksCalendar> {
        return try {
            val request = remote.getCelcat()
            val response = request.await()
            val requestLicense = remote.getCelcatLicense()
            val responseLicense = requestLicense.await()
            if (request.isCompleted && requestLicense.isCompleted) {
                val serializer = Persister()
                val source1 = FileUtil.writeResponseBodyToDisk(context, response.body(), "calendrier_semestre.xml")
                val source2 = FileUtil.writeResponseBodyToDisk(context, responseLicense.body(), "calendrier_semestre_license.xml")
                val semestre = serializer.read<CelcatXmlDto>(CelcatXmlDto::class.java, source1)
                val semestreLicense = serializer.read<CelcatXmlDto>(CelcatXmlDto::class.java, source2)
                convert(semestre, semestreLicense, local.getGroups(), localPrimaryGroup.getPrimaryGroups())
            } else emptyList()
        } catch (exception: Exception) {
            Log.e(
                CelcatUseCase::class.java.simpleName,
                "Failed : download Celcat xml file and insert in database", exception
            )
            emptyList()
        }
    }

    private fun convert(celcatXmlDto: CelcatXmlDto, celcatLicenseXmlDto: CelcatXmlDto, groups: List<Groups>, primaryGroups: List<PrimaryGroups>): List<TasksCalendar> {
        val result = ArrayList<TasksCalendar>()
        val sortedCelcat = celcatXmlDto.event.sortedWith(compareBy { it.date })
        val sortedCelcatLicense = celcatLicenseXmlDto.event.sortedWith(compareBy { it.date })

        result.addAll(addToListForResult(sortedCelcat, groups, primaryGroups))
        result.addAll(addToListForResult(sortedCelcatLicense, groups, primaryGroups))

        return result
    }

    private fun addToListForResult(sortedData: List<EventDto>, groups: List<Groups>, primaryGroups: List<PrimaryGroups>) : List<TasksCalendar> {
        val result = ArrayList<TasksCalendar>()
        for (event in sortedData) {
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
                        "Celcat",
                        group?.newGroups ?: groupBuilded,
                        event.notes
                    )
                )
            }

            if (group == null &&
                DateUtil.daysBetween(Calendar.getInstance().timeInMillis,
                    DateUtil.formatDateSlash(DateUtil.addDayToDateString(event.date, event.day)).time) >= 0) {
                local.insert(Groups(groupBuilded, groupBuilded, true))
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