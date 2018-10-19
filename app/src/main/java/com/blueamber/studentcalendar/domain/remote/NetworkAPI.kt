package com.blueamber.studentcalendar.domain.remote

import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface NetworkAPI {

    @GET("https://edt-st.u-bordeaux.fr/etudiants/Master/Master1/Semestre1/g267904.xml")
    fun getCelcatDto(): Deferred<Response<CelcatXmlDto>>

    @GET("https://raw.githubusercontent.com/master-bioinfo-bordeaux/master-bioinfo-bordeaux.github.io/master/data/calendar_m1.json")
    fun getCalendarDto(): Deferred<Response<Map<String, CalendarJsonDto>>>
}