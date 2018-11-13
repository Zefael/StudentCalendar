package com.blueamber.studentcalendar.ui.settings

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.TasksCalendarDao
import javax.inject.Inject

class SettingsViewModel  @Inject constructor(
    private val app: Application,
    private val locale: TasksCalendarDao
) : ViewModel() {

}