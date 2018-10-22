package com.blueamber.studentcalendar.domain.remote

import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import javax.inject.Inject

class NetworkXmlRepository @Inject constructor(private val xmlApi: NetworkXmlApi) {

    fun getCelcat(): Deferred<Response<CelcatXmlDto>> = xmlApi.getCelcatDto()
}