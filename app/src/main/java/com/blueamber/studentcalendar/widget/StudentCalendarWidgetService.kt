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

    override fun onCreate() {
        Log.d(TAG, "onCreate")
    }

    private fun initCursor() {
        Log.d(TAG, "initCursor")
        val identityToken = Binder.clearCallingIdentity()
        cursor = context.contentResolver.query(
            Uri.parse("content://com.blueamber.studentcalendar.provider/eventcalendar"),
            null, null, null, null)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged")
        initCursor()
        cursor?.moveToFirst()
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {
        Log.d(TAG, "getViewAt")
        val remoteViews = RemoteViews(context.packageName, R.layout.app_widget_item_event_list)
        cursor?.moveToPosition(position)
        Log.d(TAG, "cursor column names = " + cursor?.columnNames)
        remoteViews.setTextViewText(R.id.item_event_date, cursor?.getString(cursor?.getColumnIndex("date") ?: 0))
        remoteViews.setTextViewText(R.id.item_card_title, cursor?.getString(cursor?.getColumnIndex("titre") ?: 0))
        remoteViews.setTextViewText(R.id.item_card_hours_place, cursor?.getString(cursor?.getColumnIndex("hourStart") ?: 0))
        remoteViews.setTextViewText(R.id.item_card_place, cursor?.getString(cursor?.getColumnIndex("rooms") ?: 0))
        remoteViews.setTextViewText(R.id.item_card_groups, cursor?.getString(cursor?.getColumnIndex("group") ?: 0))
        return remoteViews
    }

    override fun getCount(): Int {
        return cursor?.count ?: 0
    }

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        cursor?.close()
    }
}