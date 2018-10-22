package com.blueamber.studentcalendar.ui.calendartasks

import android.arch.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.usecases.CelcatUseCase
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class CalendarTasksViewModel @Inject constructor(private val remote: NetworkXmlRepository, private val locale: DayDao) :
    ViewModel() {

    fun downloadCalendars() {
        launch {
            val isCelcatDownload = CelcatUseCase(remote, locale).downloadCelcat()
        }
    }
}
