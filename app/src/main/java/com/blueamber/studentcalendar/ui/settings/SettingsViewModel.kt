package com.blueamber.studentcalendar.ui.settings

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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
}