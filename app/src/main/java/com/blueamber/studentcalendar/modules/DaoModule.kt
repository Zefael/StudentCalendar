package com.blueamber.studentcalendar.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.blueamber.studentcalendar.domain.local.DayDao
import com.blueamber.studentcalendar.domain.local.StudentCalendarDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DaoModule {

    @Singleton
    @Provides
    fun provideMondialDatabase(context: Context): StudentCalendarDatabase {
        return Room
            .databaseBuilder(context, StudentCalendarDatabase::class.java, "student_calendar_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDayDao(studentCalendarDatabase: StudentCalendarDatabase): DayDao {
        return studentCalendarDatabase.dayDao()
    }
}