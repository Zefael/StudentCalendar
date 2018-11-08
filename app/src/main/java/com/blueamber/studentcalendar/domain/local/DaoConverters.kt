package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.TypeConverter
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.models.TypeOfSource
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
    fun fromStringToTypeOfSource(data: String): TypeOfSource {
        return TypeOfSource.valueOf(data)
    }

    @TypeConverter
    fun fromTypeOfSourceToStringTypeOfSource(data: TypeOfSource): String {
        return data.name
    }
}