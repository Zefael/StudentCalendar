package com.blueamber.studentcalendar.ui.calendartasks

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.TasksCalendarDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.usecases.CalendarUseCase
import com.blueamber.studentcalendar.domain.usecases.CelcatUseCase
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
    private val locale: TasksCalendarDao
) : ViewModel() {

    val dataDownloaded = MutableLiveData<List<TasksCalendar>>()
    val titleToolBar = MutableLiveData<String>()

    fun downloadCalendars() = launch {
        locale.deleteTasks()
        val dataCelcat = CelcatUseCase(remoteXml, locale).downloadCelcat(app)
        val dataOther = CalendarUseCase(remoteJson, locale).downloadJsonCalendar()
        locale.insert(sortDataDay(dataOther, dataCelcat))
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
}
