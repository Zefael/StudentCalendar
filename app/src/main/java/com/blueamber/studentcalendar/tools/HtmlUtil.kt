package com.blueamber.studentcalendar.tools

import android.content.Context
import android.os.Build
import android.text.Html

object HtmlUtil {

    fun loadFromAssets(context: Context, filename: String) : String? {
        try {
            context.assets.open(filename).bufferedReader().use {
                return it.readText()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return null
    }

    fun fromHtml(raw: String) =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { Html.fromHtml(raw, Html.FROM_HTML_MODE_LEGACY) }
            else { Html.fromHtml(raw) }

}