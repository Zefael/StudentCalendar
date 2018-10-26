package com.blueamber.studentcalendar.domain.remote.dtos.calendarjson

data class CalendarJsonDto(val ID: String = "",
                           val apogee: String = "",
                           val acronym: String = "",
                           val date_start: String = "",
                           val date_end: String = "",
                           val lecturer: String = "",
                           val location: String = "",
                           val group: String = "",
                           val type: String = "",
                           val year: String = "",
                           val tracks: String = "",
                           val comment: String = "")