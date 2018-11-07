package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.TypeConverter
import com.blueamber.studentcalendar.models.TasksCalendar
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*

class DaoConverters {

    @TypeConverter
    fun fromTimestampToDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun fromDateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromJsonToWork(json: String): List<TasksCalendar> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, TasksCalendar::class.java)
        val adapter : JsonAdapter<List<TasksCalendar>> = moshi.adapter(type)

        return adapter.fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun fromWorkToJson(tasks: List<TasksCalendar>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, TasksCalendar::class.java)
        val adapter : JsonAdapter<List<TasksCalendar>> = moshi.adapter(type)

        return adapter.toJson(tasks) ?: ""
    }
}