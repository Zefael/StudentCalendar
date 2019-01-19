package com.blueamber.studentcalendar.domain.remote

import kotlinx.coroutines.experimental.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class NetworkXmlRepository @Inject constructor(private val xmlApi: NetworkXmlApi) {

    fun getCelcat(): Deferred<Response<ResponseBody>> = xmlApi.getCelcatDto()

    fun getCelcatLicense(): Deferred<Response<ResponseBody>> = xmlApi.getCelcatLicenseDto()
}