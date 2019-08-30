package com.blueamber.studentcalendar.domain.remote

import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import javax.inject.Inject

class NetworkJsonRepository @Inject constructor(private val jsonApi: NetworkJsonApi) {

    fun getCalendar(m1: Boolean): Deferred<Response<Map<String, CalendarJsonDto>>> {
        return if (m1) {
            jsonApi.getCalendarM1Dto()
        } else {
            jsonApi.getCalendarM2Dto()
        }
    }
}