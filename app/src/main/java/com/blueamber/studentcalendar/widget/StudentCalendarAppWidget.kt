package com.blueamber.studentcalendar.widget

import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.widget.RemoteViews
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.ui.StudentCalendarActivity
import java.text.SimpleDateFormat
import java.util.*


class StudentCalendarAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        context.startService(Intent(context, UpdateService::class.java))
    }

    class UpdateService : Service() {

        override fun onBind(intent: Intent?): IBinder? = null

        override fun onCreate() {
            val thisWidget = ComponentName(this, StudentCalendarAppWidget::class.java)
            val manager = AppWidgetManager.getInstance(this)

            for (appWidgetId in manager.getAppWidgetIds(thisWidget)) {
                val updateViews = buildUpdate(this, appWidgetId)

                manager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_event_list)
                manager.updateAppWidget(thisWidget, updateViews)
            }
        }

        private fun buildUpdate(context: Context, appWidgetId: Int): RemoteViews {
            val views = RemoteViews(context.packageName, R.layout.student_calendar_app_widget)

            // Init adapter
            val intentPlanning = Intent(context, StudentCalendarWidgetService::class.java)
            intentPlanning.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intentPlanning.data = Uri.parse(intentPlanning.toUri(Intent.URI_INTENT_SCHEME))
            views.setRemoteAdapter(R.id.appwidget_event_list, intentPlanning)

            // Starting application when click on date
            val intent = Intent(context, StudentCalendarActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.appwidget_date, pendingIntent)

            // Title widget (date)
            val formatter = SimpleDateFormat("EEEE dd MMMM", Locale.FRANCE)
            val today = Date()
            val date = formatter.format(today)
            views.setTextViewText(R.id.appwidget_date, date)

            return views
        }
    }
}

