package com.blueamber.studentcalendar.ui.calendartasks

import android.arch.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.usecases.CalendarUseCase
import com.blueamber.studentcalendar.domain.usecases.CelcatUseCase
import kotlinx.coroutines.experimental.launch

class CalendarTasksViewModel(private val celcatUseCase: CelcatUseCase, private val calendarUseCase: CalendarUseCase) : ViewModel() {

    fun downloadCalendars() {
        launch {
            val isCelcatDownload = celcatUseCase.downloadCelcat()
        }
    }
}
