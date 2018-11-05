package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.Room
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.blueamber.studentcalendar.tools.DateUtil

private const val DBNAME = "student_calendar_db"

class StudentCalendarProvider : ContentProvider() {

    private lateinit var appDatabase: StudentCalendarDatabase
    private var dayDao: DayDao? = null

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.blueamber.studentcalendar.provider", "eventcalendar", 1)
    }


    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?,
        sortOrder: String?): Cursor? {
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            1 -> {
                cursor = dayDao?.selectAllBeginToday(DateUtil.yesterday())
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        cursor?.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun onCreate(): Boolean {
        appDatabase = Room.databaseBuilder(context, StudentCalendarDatabase::class.java, DBNAME).build()
        dayDao = appDatabase.dayDao()
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return ""
    }
}