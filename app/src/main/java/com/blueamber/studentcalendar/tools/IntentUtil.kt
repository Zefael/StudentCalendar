package com.blueamber.studentcalendar.tools

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabsIntent
import com.enterprise.baseproject.extensions.asColor

object IntentUtil {

    fun sendEmail(context: Context, email: String, subject: String = "", text: String = "") {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))

        if(!subject.isEmpty()) { intent.putExtra(Intent.EXTRA_SUBJECT, subject) }
        if(!text.isEmpty()) { intent.putExtra(Intent.EXTRA_TEXT, text) }

        if(intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun openBrowser(context: Context, uri: Uri, shareable : Boolean = false, @ColorRes toolbarColor: Int? = null) {
        val builder = CustomTabsIntent.Builder()

        if(shareable) { builder.addDefaultShareMenuItem() }
        toolbarColor?.let {
            builder.setToolbarColor(it.asColor(context))
        }

        builder.build().launchUrl(context, uri)
    }

    fun open(context: Context, uri: Uri) {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    fun share(context: Context, title: String, msg: String = "", url: String = "", intentLabel: String = "") {
        var text = if(msg.isEmpty()) {title} else {msg}
        if(url.isNotEmpty()) {text += "\n $url"}

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)

        context.startActivity(Intent.createChooser(shareIntent, intentLabel))
    }

}