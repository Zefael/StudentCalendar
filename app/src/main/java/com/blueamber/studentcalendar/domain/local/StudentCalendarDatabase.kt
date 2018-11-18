package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.TasksCalendar

@Database(entities = [TasksCalendar::class, Groups::class], version = 3, exportSchema = true)
@TypeConverters(DaoConverters::class)
abstract class StudentCalendarDatabase : RoomDatabase() {

    abstract fun tasksCalendarDao() : TasksCalendarDao

    abstract fun groupsDao() : GroupsDao

}