package com.blueamber.studentcalendar.ui.addtask

import android.app.Application
import androidx.lifecycle.ViewModel
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.TasksCalendarDao
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.tools.ColorUtil
import kotlinx.android.synthetic.main.add_task_fragment.*
import kotlinx.coroutines.experimental.launch
import java.util.*
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val app: Application,
    private val localeTasks: TasksCalendarDao
) : ViewModel() {

    private var date : Calendar? = null
    private var hoursStart = ""
    private var hoursEnd = ""

    fun updateDate(cal: Calendar) { date = cal }

    fun updateHoursStart(hour: String) { hoursStart = hour }

    fun updateHoursEnd(hour: String) { hoursEnd = hour }

    fun isDataValidate(title: String, place: String, comment: String): Boolean {
        var result = false
        if (title.isNotEmpty() && date != null && hoursStart.isNotEmpty() &&
            hoursEnd.isNotEmpty() && place.isNotEmpty()) {
            val newTask = TasksCalendar(date!!.time,
                title,
                "personnel",
                TypeOfSource.PERSONAL,
                R.color.colorAccent,
                hoursStart,
                hoursEnd,
                "",
                place,
                "perso",
                comment)
            launch { localeTasks.insert(newTask) }
            result = true
        }
        return result
    }
}