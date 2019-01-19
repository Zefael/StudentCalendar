package com.blueamber.studentcalendar.ui.settings

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
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val app: Application,
    private val remoteXml: NetworkXmlRepository,
    private val remoteJson: NetworkJsonRepository,
    private val localeGroups: GroupsDao,
    private val localeTasks: TasksCalendarDao
) : ViewModel() {

    val groups = MutableLiveData<List<Groups>>()

    fun downloadGroups() = launch {
        val result = localeGroups.getGroups()
        withContext(UI) { groups.value = result }
    }

    fun updateVisibility(newVisibility: Boolean, group: String) = launch {
        localeGroups.updateVisibility(newVisibility, group)
    }

    fun updateNewGroup(newGroup: String, group: String) = launch { localeGroups.updateNewGroup(newGroup, group) }

    fun changeAllVisibility(isVisible: Boolean) = launch {
        groups.value?.forEach { it -> localeGroups.updateVisibility(isVisible, it.originalGroups) }
        downloadGroups()
    }

    fun resetAllGroups() = launch {
        localeGroups.deleteGroups()
        CelcatUseCase(remoteXml, localeGroups).downloadCelcat(app)
        CalendarUseCase(remoteJson, localeGroups).downloadJsonCalendar()
        downloadGroups()
    }
}