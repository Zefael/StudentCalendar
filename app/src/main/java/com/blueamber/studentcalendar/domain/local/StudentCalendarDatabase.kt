package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.blueamber.studentcalendar.models.Day

@Database(entities = [Day::class], version = 1, exportSchema = true)
abstract class StudentCalendarDatabase : RoomDatabase() {

    abstract fun dayDao() : DayDao

}