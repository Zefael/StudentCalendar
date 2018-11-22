package com.blueamber.studentcalendar.domain.local

import androidx.room.TypeConverter
import com.blueamber.studentcalendar.models.TypeOfSource
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