package com.blueamber.studentcalendar.extensions

import android.net.Uri
import android.widget.ImageView
import com.blueamber.studentcalendar.tools.GlideApp

fun ImageView.glide(url: String) {
    GlideApp.with(context).load(url).into(this)
}

fun ImageView.glide(url: String, placeholder: Int) {
    GlideApp.with(context).load(url).placeholder(placeholder).into(this)
}

fun ImageView.glide(uri: Uri) {
    GlideApp.with(context).load(uri).into(this)
}

fun ImageView.glide(uri: Uri, placeholder: Int) {
    GlideApp.with(context).load(uri).placeholder(placeholder).into(this)
}