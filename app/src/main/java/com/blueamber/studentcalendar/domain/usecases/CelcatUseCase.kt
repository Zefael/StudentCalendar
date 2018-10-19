package com.blueamber.studentcalendar.domain.usecases

import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.remote.NetworkRepository
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.CelcatXmlDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.EventDto
import com.blueamber.studentcalendar.domain.remote.dtos.celcatxml.ResourcesDto
import com.blueamber.studentcalendar.models.Day
import com.blueamber.studentcalendar.models.Work
import kotlinx.coroutines.experimental.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CelcatUseCase(
    private val remote: NetworkRepository,
    private val local: DayDao) {

    fun downloadCelcat(): Boolean {
        launch {
            try {
                val request = remote.getCelcat()
                val response = request.await()
                if (request.isCompleted) {

                }
            } catch (exception: Exception) {

            }
        }
        return true
    }

    // TODO : trier celcatXML(pour un event plusieurs resources)

//    private fun sortCelcat(celcatXmlDto: CelcatXmlDto) : Map<String, List<ResourcesDto>> {
//
//    }

    private fun buildListItemToString(items: List<String>): String {
        var result = ""
        for (item in items) {
            result += item + "\n"
        }
        return result
    }

    private fun formatDate(date: String) : Date {
        return try {
            SimpleDateFormat("dd MMM yyyy", Locale.FRANCE).parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }
}