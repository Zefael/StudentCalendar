package com.enterprise.baseproject.extensions

import android.util.Patterns

fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isNotEmail() = !Patterns.EMAIL_ADDRESS.matcher(this).matches()