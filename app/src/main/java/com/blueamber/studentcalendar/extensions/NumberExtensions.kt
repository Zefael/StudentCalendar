package com.enterprise.baseproject.extensions

import android.content.res.Resources

fun Int.toDp() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.clamp(min: Int, max: Int) = Math.max(min, Math.min(this, max))