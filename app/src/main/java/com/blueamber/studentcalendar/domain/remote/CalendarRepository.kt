package com.blueamber.studentcalendar.domain.remote

import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val api: NetworkAPI) {

    fun getCelcat() : Deferred<Response<CelcatXmlDto>> = api.getCelcatDto()

    fun getCalendar() : Deferred<Response<Map<String, CalendarJsonDto>>> = api.getCalendarDto()
}