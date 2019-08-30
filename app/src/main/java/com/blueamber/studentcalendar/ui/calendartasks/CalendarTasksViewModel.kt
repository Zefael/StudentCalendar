package com.blueamber.studentcalendar.ui.calendartasks

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blueamber.studentcalendar.PrefKeys
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.PrimaryGroupsDao
import com.blueamber.studentcalendar.domain.local.TasksCalendarDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.usecases.CalendarUseCase
import com.blueamber.studentcalendar.domain.usecases.CelcatUseCase
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.tools.DateUtil
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class CalendarTasksViewModel @Inject constructor(
    private val app: Application,
    private val remoteXml: NetworkXmlRepository,
    private val remoteJson: NetworkJsonRepository,
    private val locale: TasksCalendarDao,
    private val localePrimaryGroups: PrimaryGroupsDao,
    private val localeGroups: GroupsDao
) : ViewModel() {

    val dataDownloaded = MutableLiveData<List<TasksCalendar>>()
    val titleToolBar = MutableLiveData<String>()

    fun downloadCalendars() = launch {
        locale.deleteTasks()
        if (Prefs.getBoolean(PrefKeys.MASTER_SELECTED_IS_ONE, true)) {
            val dataCelcat = CelcatUseCase(remoteXml, localeGroups, localePrimaryGroups).downloadCelcat(app)
            locale.insert(dataCelcat)
        }
        val dataOther = CalendarUseCase(remoteJson, localeGroups, localePrimaryGroups).downloadJsonCalendar()
        locale.insert(dataOther)
        val dataDay = locale.getTasksAfterDate(DateUtil.yesterday())
        withContext(UI) { dataDownloaded.value = dataDay.sorted() }
    }

    fun setToolbarTitle(title: String) {
        titleToolBar.value = title
    }
}
