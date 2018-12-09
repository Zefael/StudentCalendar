package com.blueamber.studentcalendar.ui.addtask

import android.app.Application
import androidx.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.TasksCalendarDao
import java.util.*
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val app: Application,
    private val localeGroups: GroupsDao,
    private val localeTasks: TasksCalendarDao
) : ViewModel() {

    var date = Calendar.getInstance()
    var hoursStart = ""
    var hoursEnd = ""

    fun updateDate(cal: Calendar) { date = cal }

    fun updateHoursStart(hour: String) { hoursStart = hour }

    fun updateHoursEnd(hour: String) { hoursEnd = hour }

    fun validate(title: String, place: String, comment: String) {

    }
}