package com.blueamber.studentcalendar.ui.calendartasks

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkJsonRepository
import com.blueamber.studentcalendar.domain.remote.NetworkXmlRepository
import com.blueamber.studentcalendar.domain.usecases.CalendarUseCase
import com.blueamber.studentcalendar.domain.usecases.CelcatUseCase
import com.blueamber.studentcalendar.models.Day
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class CalendarTasksViewModel @Inject constructor(
    private val remoteXml: NetworkXmlRepository,
    private val remoteJson: NetworkJsonRepository,
    private val locale: DayDao
) : ViewModel() {

    val isDataDownloaded = MutableLiveData<Boolean>()

    fun downloadCalendars() {
        launch {
            val isCelcatDownloaded = CelcatUseCase(remoteXml, locale).downloadCelcat()
            val isJsonCalendarDownlaoded = CalendarUseCase(remoteJson, locale).downloadJsonCalendar()
            withContext(UI) { isDataDownloaded.value = (isCelcatDownloaded && isJsonCalendarDownlaoded)}
        }
    }

    fun getDataCalendarFromDatabase(): List<Day> {
        return async { locale.getDays() }.getCompleted()
    }

    fun setToolbarTitle(title: String) {

    }
}
