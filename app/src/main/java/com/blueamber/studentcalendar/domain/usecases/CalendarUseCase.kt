package com.blueamber.studentcalendar.domain.usecases

import android.util.Log
import com.blueamber.studentcalendar.PrefKeys
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.PrimaryGroupsDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.PrimaryGroups
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.tools.ColorUtil
import com.blueamber.studentcalendar.tools.DateUtil
import com.pixplicity.easyprefs.library.Prefs
import java.util.*

class CalendarUseCase(private val remote: NetworkJsonRepository, private val locale: GroupsDao,
                      private  val localePrimaryGroup: PrimaryGroupsDao) {

    suspend fun downloadJsonCalendar(): List<TasksCalendar> {
        return try {
            val request = remote.getCalendar(Prefs.getBoolean(PrefKeys.MASTER_SELECTED_IS_ONE, true))
            val response = request.await()
            if (request.isCompleted) {
                convert(response.body() ?: emptyMap(), locale.getGroups(), localePrimaryGroup.getPrimaryGroups())
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

    private fun convert(data: Map<String, CalendarJsonDto>, groups: List<Groups>, primaryGroups: List<PrimaryGroups>): List<TasksCalendar> {
        val result = ArrayList<TasksCalendar>()

        data.forEach { (_, item) ->
            val group = groups.find { it.originalGroups == item.group }
            val primaryGroup = primaryGroups.find { it.originalPrimaryGroup == item.tracks }
            if (group?.visibility ?: true && primaryGroup?.visibility ?: true) {
                result.add(
                    TasksCalendar(
                        DateUtil.formatDateDashT(item.date_start),
                        item.acronym.substringAfter("::"),
                        item.type,
                        TypeOfSource.OTHER,
                        ColorUtil.colorForTask(item.type, TypeOfSource.OTHER),
                        item.date_start.substringAfter("T"),
                        item.date_end.substringAfter("T"),
                        item.lecturer,
                        item.location.substringAfter("::").replace("@", "/"),
                        primaryGroup?.newPrimaryGroup ?: item.tracks,
                        group?.newGroups ?: item.group,
                        if (item.comment.contains("Imported")) "" else (item.comment)
                    )
                )
            }

            if (group == null &&
                DateUtil.daysBetween(Calendar.getInstance().timeInMillis, DateUtil.formatDateDashT(item.date_start).time) >= 0) {
                locale.insert(Groups(item.group, item.group, true))
            }

            if (primaryGroup == null) {
                localePrimaryGroup.insert(PrimaryGroups(item.tracks, item.tracks, true))
            }
        }
        return result
    }
}