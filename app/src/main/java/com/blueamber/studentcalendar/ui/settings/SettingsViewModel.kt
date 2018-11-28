package com.blueamber.studentcalendar.ui.settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.models.Groups
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val app: Application,
    private val locale: GroupsDao
) : ViewModel() {

    val groups = MutableLiveData<List<Groups>>()

    fun downloadGroups() = launch{
        val result = locale.getGroups()
        withContext(UI) { groups.value = result }
    }

    fun updateVisibility(newVisibility: Boolean, group: String) {
        launch {
            locale.updateVisibility(newVisibility, group)
        }
    }

    fun updateNewGroup(newGroup: String, group: String) {
        launch { locale.updateNewGroup(newGroup, group) }
    }

    fun changeAllVisibility() {
        groups.value?.forEach { it -> launch { locale.updateVisibility(true, it.originalGroups) } }
    }
}