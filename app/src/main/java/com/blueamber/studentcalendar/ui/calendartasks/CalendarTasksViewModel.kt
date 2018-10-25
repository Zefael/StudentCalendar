package com.blueamber.studentcalendar.ui.calendartasks

import android.arch.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.usecases.CelcatUseCase
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class CalendarTasksViewModel @Inject constructor(
    private val remoteXml: NetworkXmlRepository,
    private val remoteJson: NetworkJsonRepository,
    private val locale: DayDao
) : ViewModel() {

    // TODO use Mutable live data for contain boolean dowload

    fun downloadCalendars() {
        launch {
            val isCelcatDownloaded = CelcatUseCase(remoteXml, locale).downloadCelcat()
//            val isJsonCalendarDownlaoded = CalendarUseCase(remoteJson, locale).downloadJsonCalendar()
        }
    }

    fun setToolbarTitle(title: String) {

    }
}
