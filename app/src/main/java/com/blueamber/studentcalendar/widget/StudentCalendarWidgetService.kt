package com.blueamber.studentcalendar.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.blueamber.studentcalendar.R


class StudentCalendarWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StudentCalendarWidgetFactory(this.applicationContext, intent)
}

class StudentCalendarWidgetFactory(val context: Context, val intent: Intent?) : RemoteViewsService.RemoteViewsFactory {

    private val TAG = StudentCalendarWidgetFactory::class.java.name
    private var cursor: Cursor? = null
    private var countOfCursor : Cursor? = null

    override fun onCreate() {
        Log.d(TAG, "onCreate")
        initCursor()
        cursor?.moveToFirst()
    }

    private fun initCursor() {
        Log.d(TAG, "initCursor")
        val identityToken = Binder.clearCallingIdentity()
        cursor = context.contentResolver.query(
            Uri.parse("content://com.blueamber.studentcalendar.provider/eventcalendar"),
            null, null, null, null)
        countOfCursor = context.contentResolver.query(
            Uri.parse("content://com.blueamber.studentcalendar.provider/count"),
            null, null, null, null)
        Binder.restoreCallingIdentity(identityToken)
        Log.d(TAG, "cursor column names = " + countOfCursor?.getInt(cursor?.getColumnIndex("COUNT(*)") ?: 0))
    }

    override fun getLoadingView(): RemoteViews {
        Log.d(TAG, "getLoadingView")
        return RemoteViews(context.packageName, R.layout.loading_view_app_widget)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged")
        initCursor()
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {
        Log.d(TAG, "getViewAt")
        val remoteViews = RemoteViews(context.packageName, R.layout.app_widget_item_event_list)
        cursor?.moveToPosition(position)
        Log.d(TAG, "cursor = " + cursor.toString())
//        val work: List<Work> = Gson().fromJson(cursor.toString(), object : TypeToken<List<Work>>() {}.type)
//            val dayOfMonth = DateUtil.dayOfMonth().toString()
//            val dayOfWeek = DateUtil.dayOfWeek(day.date.time)
        remoteViews.setTextViewText(R.id.item_event_date, cursor?.getString(cursor?.getColumnIndex("date") ?: 0))
//        remoteViews.setTextViewText(R.id.item_card_title, work.get(0).titre)
        return remoteViews
    }

    override fun getCount(): Int {
        return countOfCursor?.getInt(0) ?: 0
    }

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        cursor?.close()
    }
}