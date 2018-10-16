package com.blueamber.studentcalendar.domain.remote

import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import retrofit2.http.GET

interface NetworkAPI {

    @GET("myModels")
    fun getCelcatDTO(): CelcatXmlDto

    @GET("myModels")
    fun getCalendarDTO(): Map<String, CalendarJsonDto>
}