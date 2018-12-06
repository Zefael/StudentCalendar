package com.blueamber.studentcalendar.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blueamber.studentcalendar.Constants
import com.blueamber.studentcalendar.StudentCalendarApp
import com.emas.mondial.ui.main.Back
import com.emas.mondial.ui.main.LoadFragment
import com.emas.mondial.ui.main.NavigationState
import java.util.*

class MainViewModel : ViewModel() {

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent


    val navigation = MutableLiveData<NavigationState>()
    val statusBarColor = MutableLiveData<Int>()
    val statusBarTitle = MutableLiveData<String>()
    val statusBarOptionIcon = MutableLiveData<Int>()
    val refreshNeeded = MutableLiveData<Boolean>()

    fun back() {
        navigation.value = Back
    }

    fun loadFragment(fragment: NavigationFragment) {
        navigation.value = LoadFragment(fragment)
    }

    fun setStatusBarColor(color: Int) {
        statusBarColor.value = color
    }

    fun setStatusBarTitle(title: String) {
        statusBarTitle.value = title
    }

    fun updateAlarmClock(context: Context, calendar: Calendar) {
        if (alarmMgr == null) {
            initAlarmMgn(context)
        } else {
            cancelAlarmClock(context)
        }
        alarmMgr?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60 * Constants.TIME_TO_REPEATING_ALARM_IN_MINUTE,
            alarmIntent
        )
    }

    fun cancelAlarmClock(context: Context) {
        if (alarmMgr == null) {
            initAlarmMgn(context)
        }
        alarmMgr?.cancel(alarmIntent)
    }

    private fun initAlarmMgn(context: Context) {
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, StudentCalendarApp::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
    }
}