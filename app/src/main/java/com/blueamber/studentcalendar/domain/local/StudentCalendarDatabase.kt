package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.blueamber.studentcalendar.models.Day

@Database(entities = [Day::class], version = 1, exportSchema = true)
@TypeConverters(DaoConverters::class)
abstract class StudentCalendarDatabase : RoomDatabase() {

    abstract fun dayDao() : DayDao

}