package com.enterprise.baseproject.extensions

import android.content.Context
import android.widget.Toast

fun String.toast(context: Context, duration: Int = Toast.LENGTH_LONG): Toast {
    return Toast.makeText(context, this, duration).apply { show() }
}

fun Int.toast(context: Context, duration: Int = Toast.LENGTH_LONG): Toast {
    return Toast.makeText(context, context.getString(this), duration).apply { show() }
}
