package com.blueamber.studentcalendar.ui.settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blueamber.studentcalendar.PrefKeys
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.PrimaryGroupsDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.usecases.CalendarUseCase
import com.blueamber.studentcalendar.domain.usecases.CelcatUseCase
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.PrimaryGroups
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val app: Application,
    private val remoteXml: NetworkXmlRepository,
    private val remoteJson: NetworkJsonRepository,
    private val localeGroups: GroupsDao,
    private val localePrimaryGroups: PrimaryGroupsDao
) : ViewModel() {

    val groups = MutableLiveData<List<Groups>>()
    val primaryGroups = MutableLiveData<List<PrimaryGroups>>()

    fun downloadGroups() = launch {
        val resultGroups = localeGroups.getGroups()
        val resultPrimaryGroups = localePrimaryGroups.getPrimaryGroups()
        withContext(UI) {
            groups.value = resultGroups
            primaryGroups.value = resultPrimaryGroups
        }
    }

    fun updateVisibilityGroup(newVisibility: Boolean, group: String) = launch {
        localeGroups.updateVisibility(newVisibility, group)
    }

    fun updateVisibilityPrimaryGroup(newVisibility: Boolean, group: String) = launch {
        localePrimaryGroups.updateVisibility(newVisibility, group)
    }

    fun updateNewGroup(newGroup: String, group: String) = launch { localeGroups.updateNewGroup(newGroup, group) }

    fun updateNewPrimaryGroup(newGroup: String, group: String) = launch { localePrimaryGroups.updateNewPrimaryGroup(newGroup, group) }

    fun changeAllVisibility(isVisible: Boolean) = launch {
        groups.value?.forEach { it -> localeGroups.updateVisibility(isVisible, it.originalGroups) }
        downloadGroups()
    }

    fun resetAllGroups() = launch {
        localeGroups.deleteGroups()
        if (Prefs.getBoolean(PrefKeys.MASTER_SELECTED_IS_ONE, true)) {
            CelcatUseCase(remoteXml, localeGroups, localePrimaryGroups).downloadCelcat(app)
        }
        CalendarUseCase(remoteJson, localeGroups, localePrimaryGroups).downloadJsonCalendar()
        downloadGroups()
    }
}