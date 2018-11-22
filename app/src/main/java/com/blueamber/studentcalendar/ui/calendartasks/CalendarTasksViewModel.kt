package com.blueamber.studentcalendar.ui.calendartasks

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.TasksCalendarDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.usecases.CalendarUseCase
import com.blueamber.studentcalendar.domain.usecases.CelcatUseCase
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.tools.DateUtil
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.*
import javax.inject.Inject

class CalendarTasksViewModel @Inject constructor(
    private val app: Application,
    private val remoteXml: NetworkXmlRepository,
    private val remoteJson: NetworkJsonRepository,
    private val locale: TasksCalendarDao,
    private val localeGroups: GroupsDao
) : ViewModel() {

    val dataDownloaded = MutableLiveData<List<TasksCalendar>>()
    val titleToolBar = MutableLiveData<String>()

    fun downloadCalendars() = launch {
        locale.deleteTasks()
        localeGroups.deleteGroups()
        val dataCelcat = CelcatUseCase(remoteXml, localeGroups).downloadCelcat(app)
        val dataOther = CalendarUseCase(remoteJson, localeGroups).downloadJsonCalendar()
        val tasksForInsert = sortDataDay(dataOther, dataCelcat)
        localeGroups.insert(createListOfGroups(tasksForInsert))
        locale.insert(tasksForInsert)
        val dataDay = locale.getTasksAfterDate(DateUtil.yesterday())
        withContext(UI) { dataDownloaded.value = dataDay }
    }

    private fun sortDataDay(data1: List<TasksCalendar>, data2: List<TasksCalendar>): List<TasksCalendar> {
        val result = ArrayList<TasksCalendar>()
        result.addAll(data1)
        result.addAll(data2)
        return result.sorted()
    }

    fun setToolbarTitle(title: String) {
        titleToolBar.value = title
    }

    private fun createListOfGroups(tasksList: List<TasksCalendar>): List<Groups> {
        val result = ArrayList<Groups>()
        tasksList.forEach {
            val group = Groups(it.group, it.group, true)
            result.add(group)
        }
        return result
    }
}
