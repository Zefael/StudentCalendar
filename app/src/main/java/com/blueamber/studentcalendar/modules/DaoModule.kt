package com.blueamber.studentcalendar.modules

import androidx.room.Room
import android.content.Context
import com.blueamber.studentcalendar.domain.local.GroupsDao
import com.blueamber.studentcalendar.domain.local.StudentCalendarDatabase
import com.blueamber.studentcalendar.domain.local.TasksCalendarDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DaoModule {

    @Singleton
    @Provides
    fun provideStudentCalendarDatabase(context: Context): StudentCalendarDatabase {
        return Room
            .databaseBuilder(context, StudentCalendarDatabase::class.java, "student_calendar_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTasksDao(studentCalendarDatabase: StudentCalendarDatabase): TasksCalendarDao {
        return studentCalendarDatabase.tasksCalendarDao()
    }

    @Singleton
    @Provides
    fun provideGroupsDao(studentCalendarDatabase: StudentCalendarDatabase): GroupsDao {
        return studentCalendarDatabase.groupsDao()
    }
}