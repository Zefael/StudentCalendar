package com.blueamber.studentcalendar.domain.remote

import com.blueamber.studentcalendar.domain.remote.dtos.calendarjson.CalendarJsonDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import retrofit2.http.GET

interface NetworkAPI {

    @GET("https://edt-st.u-bordeaux.fr/etudiants/Master/Master1/Semestre1/g267904.xml")
    fun getCelcatDTO(): CelcatXmlDto

    @GET("https://raw.githubusercontent.com/master-bioinfo-bordeaux/master-bioinfo-bordeaux.github.io/master/data/calendar_m1.json")
    fun getCalendarDTO(): Map<String, CalendarJsonDto>
}