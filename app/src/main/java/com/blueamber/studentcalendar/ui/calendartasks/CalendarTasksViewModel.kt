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
    val firstTaskVisible = MutableLiveData<TasksCalendar>()

    fun downloadCalendars() = launch {
        locale.deleteTasks()
        val dataCelcat = CelcatUseCase(remoteXml, localeGroups).downloadCelcat(app)
        val dataOther = CalendarUseCase(remoteJson, localeGroups).downloadJsonCalendar()
        locale.insert(dataCelcat)
        locale.insert(dataOther)
        val dataDay = locale.getTasksAfterDate(DateUtil.yesterday())
        withContext(UI) { dataDownloaded.value = dataDay.sorted() }
    }

    fun setToolbarTitle(title: String) {
        titleToolBar.value = title
    }

    fun getFirstTaskVisibleForUpdateAlarm() = launch {
        val result = locale.getFirstTask(DateUtil.yesterday())
        withContext(UI) { firstTaskVisible.value = result }
    }
}
