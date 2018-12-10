package com.blueamber.studentcalendar.tools

import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.TypeOfSource

object ColorUtil {

    fun colorForTask(category: String, typeOfSource: TypeOfSource): Int {
        return when (category) {
            "Cours" -> getCardLesson(typeOfSource)
            "TD" -> getCardTD(typeOfSource)
            "Cours+TD" -> getCardLessonAndTD(typeOfSource)
            "TD machine" -> R.color.red
            else -> R.color.grey
        }
    }

    private fun getCardLesson(typeOfSource: TypeOfSource): Int {
        return when (typeOfSource) {
            TypeOfSource.CELCAT -> R.color.blue_light
            TypeOfSource.OTHER -> R.color.orange
            TypeOfSource.PERSONAL -> R.color.colorAccent
        }
    }

    private fun getCardTD(typeOfSource: TypeOfSource): Int {
        return when (typeOfSource) {
            TypeOfSource.CELCAT -> R.color.blue
            TypeOfSource.OTHER -> R.color.red
            TypeOfSource.PERSONAL -> R.color.colorAccent
        }
    }

    private fun getCardLessonAndTD(typeOfSource: TypeOfSource): Int {
        return when (typeOfSource) {
            TypeOfSource.CELCAT -> R.color.blue
            TypeOfSource.OTHER -> R.color.red
            TypeOfSource.PERSONAL -> R.color.colorAccent
        }
    }
}