package com.blueamber.studentcalendar.domain.remote

import javax.inject.Inject

class NetworkRepository @Inject constructor(private val api: NetworkAPI) {

    fun getMyModels() : CelcatDTO = api.getCelcatDTO()

    fun getMyModels2() : CalendarJSON = api.getCalendarDTO()
}