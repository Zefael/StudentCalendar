package com.blueamber.studentcalendar.domain.remote

import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface NetworkJsonApi {

    @GET("https://raw.githubusercontent.com/master-bioinfo-bordeaux/master-bioinfo-bordeaux.github.io/master/data/calendar_m1.json")
    fun getCalendarM1Dto(): Deferred<Response<Map<String, CalendarJsonDto>>>

    @GET("")
    fun getCalendarM2Dto(): Deferred<Response<Map<String, CalendarJsonDto>>>
}