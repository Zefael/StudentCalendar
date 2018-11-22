package com.blueamber.studentcalendar.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.TasksCalendar

@Database(entities = [TasksCalendar::class, Groups::class], version = 3, exportSchema = true)
@TypeConverters(DaoConverters::class)
abstract class StudentCalendarDatabase : RoomDatabase() {

    abstract fun tasksCalendarDao() : TasksCalendarDao

    abstract fun groupsDao() : GroupsDao

}