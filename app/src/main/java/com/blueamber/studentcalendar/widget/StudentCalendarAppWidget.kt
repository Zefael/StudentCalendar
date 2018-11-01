package com.blueamber.studentcalendar.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import com.blueamber.studentcalendar.R


class StudentCalendarAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            context.startService(Intent(context, UpdateService::class.java))
        }
    }

    class UpdateService : Service() {

        override fun onBind(intent: Intent?): IBinder? = null

        override fun onCreate() {
            val updateViews = buildUpdate(this)
            val thisWidget = ComponentName(this, StudentCalendarAppWidget::class.java)
            val manager = AppWidgetManager.getInstance(this)

            manager.updateAppWidget(thisWidget, updateViews)
        }

        private fun buildUpdate(context: Context): RemoteViews {
            val views = RemoteViews(context.packageName, R.layout.student_calendar_app_widget)
            return views
        }
    }
}

