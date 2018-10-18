package com.blueamber.studentcalendar.domain.remote

import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val api: NetworkAPI) {

    fun getMyModels() : CelcatXmlDto = api.getCelcatDTO()

    fun getMyModels2() : Map<String, CalendarJsonDto> = api.getCalendarDTO()
}