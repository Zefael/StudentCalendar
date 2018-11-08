package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.Room
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.blueamber.studentcalendar.tools.DateUtil
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

private const val DBNAME = "student_calendar_db"

class StudentCalendarProvider : ContentProvider() {

    private lateinit var appDatabase: StudentCalendarDatabase
    private var tasksDao: TasksCalendarDao? = null

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.blueamber.studentcalendar.provider", "eventcalendar", 1)
        addURI("com.blueamber.studentcalendar.provider", "count", 2)
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        var cursor: Cursor? = null
        Log.d("StudentCalendarProvider", "query before runBlocking")
        runBlocking {
            Log.d("StudentCalendarProvider", "query runBlocking")
            async {
                Log.d("StudentCalendarProvider", "query a   sync")
                when (uriMatcher.match(uri)) {
                    1 -> {
                        cursor = tasksDao?.selectAllBeginToday(DateUtil.yesterday())
                    }
                }
            }.await()
        }
        Log.d("StudentCalendarProvider", "query after runBlocking")
        cursor?.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun onCreate(): Boolean {
        appDatabase = Room.databaseBuilder(context, StudentCalendarDatabase::class.java, DBNAME).build()
        tasksDao = appDatabase.tasksCalendarDao()
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