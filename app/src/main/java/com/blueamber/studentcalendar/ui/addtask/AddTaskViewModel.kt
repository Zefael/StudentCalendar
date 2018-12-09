package com.blueamber.studentcalendar.ui.addtask

import android.app.Application
import androidx.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.TasksCalendarDao
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val app: Application,
    private val localeGroups: GroupsDao,
    private val localeTasks: TasksCalendarDao
) : ViewModel() {

}