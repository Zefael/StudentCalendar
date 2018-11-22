package com.enterprise.baseproject.extensions

import android.content.Context
import androidx.core.content.ContextCompat

// Colors
fun Int.asColor(context: Context) = ContextCompat.getColor(context, this)

// Dimens
fun Int.asDp(context: Context) = context.resources.getDimensionPixelOffset(this)

// String
fun Int.asString(context: Context) = context.resources.getString(this)