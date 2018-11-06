package com.blueamber.studentcalendar.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Work
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StudentCalendarWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StudentCalendarWidgetFactory(this.applicationContext, intent)
}

class StudentCalendarWidgetFactory(val context: Context, val intent: Intent?) : RemoteViewsService.RemoteViewsFactory {

    private var cursor: Cursor? = null

    override fun onCreate() {
        initCursor()
        cursor?.moveToFirst()
    }

    private fun initCursor() {
        cursor?.close()
        val identityToken = Binder.clearCallingIdentity()
        cursor = context.contentResolver.query(
            Uri.parse("content://com.blueamber.studentcalendar.provider/eventcalendar"),
            null, null, null, null)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun getLoadingView(): RemoteViews = RemoteViews(context.packageName, R.layout.loading_view_app_widget)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onDataSetChanged() {
        initCursor()
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.app_widget_item_event_list)
        cursor?.moveToPosition(position)
//        val work: List<Work> = Gson().fromJson(cursor.toString(), object : TypeToken<List<Work>>() {}.type)
//            val dayOfMonth = DateUtil.dayOfMonth().toString()
//            val dayOfWeek = DateUtil.dayOfWeek(day.date.time)
        remoteViews.setTextViewText(R.id.item_event_date, cursor?.getString(cursor?.getColumnIndex("date") ?: 0))
//        remoteViews.setTextViewText(R.id.item_card_title, work.get(0).titre)
        return remoteViews
    }

    override fun getCount(): Int = cursor?.count ?: 0

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        cursor?.close()
    }
}