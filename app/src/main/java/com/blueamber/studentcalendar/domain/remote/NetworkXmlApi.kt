package com.blueamber.studentcalendar.domain.remote

import kotlinx.coroutines.experimental.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface NetworkXmlApi {

    @GET("https://edt-st.u-bordeaux.fr/etudiants/Master/Master1/Semestre1/g267904.xml")
    fun getCelcatDto(): Deferred<Response<ResponseBody>>

    @GET("https://edt-st.u-bordeaux.fr/etudiants/Licence/Semestre2/g295769.xml")
    fun getCelcatLicenseDto(): Deferred<Response<ResponseBody>>
}