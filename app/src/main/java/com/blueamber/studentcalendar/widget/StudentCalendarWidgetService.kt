package com.blueamber.studentcalendar.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.tools.DateUtil
import java.util.*


class StudentCalendarWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StudentCalendarWidgetFactory(this.applicationContext, intent)
}

class StudentCalendarWidgetFactory(val context: Context, val intent: Intent?) : RemoteViewsService.RemoteViewsFactory {

    private var cursor: Cursor? = null
    private var dateBefore: Date? = null

    override fun onCreate() {
    }

    private fun initCursor() {
        val identityToken = Binder.clearCallingIdentity()
        cursor = context.contentResolver.query(
            Uri.parse("content://com.blueamber.studentcalendar.provider/eventcalendar"),
            null, null, null, null
        )
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onDataSetChanged() {
        initCursor()
        cursor?.moveToFirst()
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.app_widget_item_event_list)
        cursor?.moveToPosition(position)
        val dateActual = Date(cursor?.getString(cursor?.getColumnIndex("date") ?: 0)?.toLong() ?: 0)

        if (DateUtil.isToday(dateActual.time) && !DateUtil.isNowBeforeToTasks(cursor?.getString(cursor?.getColumnIndex("hourEnd") ?: 0) ?: "")) {
            remoteViews.setViewVisibility(R.id.item_widget_remote_view, View.GONE)
            return remoteViews
        } else {
            remoteViews.setViewVisibility(R.id.item_widget_remote_view, View.VISIBLE)
        }


        if (position > 0 && DateUtil.isEqualsDate(dateActual.time, dateBefore?.time ?: 0)) {
            remoteViews.setViewVisibility(R.id.item_event_date, View.INVISIBLE)
        } else {
            remoteViews.setViewVisibility(R.id.item_event_date, View.VISIBLE)
        }

        remoteViews.setTextViewText(R.id.item_event_date, context.getString(
            R.string.date_event,
            DateUtil.dayOfMonth(dateActual.time).toString(),
            DateUtil.dayOfWeek(dateActual.time)))
        remoteViews.setTextViewText(R.id.item_card_title, cursor?.getString(cursor?.getColumnIndex("titre") ?: 0))
        remoteViews.setTextViewText(R.id.item_card_hours_place, context.getString(R.string.hours_start_end,
            cursor?.getString(cursor?.getColumnIndex("hourStart") ?: 0),
            cursor?.getString(cursor?.getColumnIndex("hourEnd") ?: 0)))
        remoteViews.setTextViewText(R.id.item_card_place, cursor?.getString(cursor?.getColumnIndex("rooms") ?: 0))
        remoteViews.setTextViewText(R.id.item_card_groups, cursor?.getString(cursor?.getColumnIndex("group") ?: 0))
        remoteViews.setInt(R.id.widget_card, "setBackgroundResource", cursor?.getInt(cursor?.getColumnIndex("colorTask") ?: 0) ?: R.color.grey)

        dateBefore = dateActual

        if (position < (cursor?.count ?:0) -1) {
            cursor?.moveToPosition(position + 1)
            val dateAfter = Date(cursor?.getString(cursor?.getColumnIndex("date") ?: 0)?.toLong() ?: 0)
            if (DateUtil.isEqualsDate(dateAfter.time, dateActual.time)) {
                remoteViews.setInt(R.id.simple_separation, "setBackgroundResource", 0)
            } else {
                remoteViews.setInt(
                    R.id.simple_separation,
                    "setBackgroundResource",
                    R.drawable.background_white_bottom_border_grey)
            }
        }

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