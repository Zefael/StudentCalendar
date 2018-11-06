package com.blueamber.studentcalendar.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.os.Binder.restoreCallingIdentity
import android.os.Binder.clearCallingIdentity
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Day
import com.blueamber.studentcalendar.tools.DateUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray


class StudentCalendarWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory = StudentCalendarWidgetFactory(this.applicationContext, intent)
}

class StudentCalendarWidgetFactory(val context: Context, val intent: Intent?) : RemoteViewsService.RemoteViewsFactory {

    private var cursor: Cursor? = null
    private var data: List<Day>? = null

    override fun onCreate() {
        initCursor()
        cursor?.moveToFirst()
    }

    private fun initCursor() {
        val identityToken = Binder.clearCallingIdentity()
        cursor = context.contentResolver.query(Uri.parse("content://com.blueamber.studentcalendar.provider/eventcalendar"),
            null, null, null, null)
        Binder.restoreCallingIdentity(identityToken)
//        data = Gson().fromJson(cursor.toString(), object : TypeToken<List<Day>>() {}.type)
        cursor?.close()
    }

    override fun getLoadingView(): RemoteViews = RemoteViews(context.packageName, R.layout.loading_view_app_widget)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onDataSetChanged() {
        initCursor()
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.app_widget_item_event_list)
        val day = data?.get(position)
        if (day != null) {
            val dayOfMonth = DateUtil.dayOfMonth(day.date.time).toString()
            val dayOfWeek = DateUtil.dayOfWeek(day.date.time)
            remoteViews.setTextViewText(R.id.item_event_date, dayOfMonth + "\n" + dayOfWeek)
        }
        return remoteViews
    }

    override fun getCount(): Int = data?.size ?: 0

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        cursor?.close()
    }
}