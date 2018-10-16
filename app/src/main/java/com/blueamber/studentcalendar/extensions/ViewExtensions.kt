package com.enterprise.baseproject.extensions

import android.support.annotation.ColorRes
import android.support.annotation.FontRes
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.webkit.WebView
import android.widget.ImageSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher
import com.enterprise.baseproject.util.HtmlUtil

// Common
fun View.animateAlpha(alpha: Float, duration: Long) {
    animate()
            .alpha(alpha)
            .setDuration(duration)
            .start()
}

fun View.toggleVisibility() {
    visibility =
            if(visibility == View.VISIBLE) { View.GONE }
            else { View.VISIBLE }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.setWidth(width: Int) {
    layoutParams.width = width
    requestLayout()
}

fun View.setHeight(height: Int) {
    layoutParams.height = height
    requestLayout()
}

fun View.setSize(width: Int, height: Int) {
    layoutParams.width = width
    layoutParams.height = height
    requestLayout()
}

fun View.animateAlphaDelayed(alpha: Float, startDelay: Long, duration: Long) {
    animate()
            .alpha(alpha)
            .setStartDelay(startDelay)
            .setDuration(duration)
            .start()
}

inline fun View.waitForLayout(crossinline f: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            f()
        }
    })
}

// ImageSwitcher
fun ImageSwitcher.setup(factory: ViewSwitcher.ViewFactory, animIn: Animation, animOut: Animation) {
    setFactory(factory)
    inAnimation = animIn
    outAnimation = animOut
}

// TextView
fun TextView.applyFont(@FontRes fontRes: Int) {
    if(fontRes != 0) { typeface = ResourcesCompat.getFont(context, fontRes)}
}

fun TextView.tintDrawable(@ColorRes colorRes: Int) {
    for(drawable in compoundDrawablesRelative.filterNotNull()) {
        drawable.mutate().setTint(colorRes.asColor(context))
    }
}

// Webview
fun WebView.loadFromAssets(filepath: String, baseUrl: String = "") {
    val content = HtmlUtil.loadFromAssets(context, filepath)
    content?.let {
        if(baseUrl.isEmpty()) {
            loadData(content, "text/html", "UTF-8")
        }
        else {
            loadDataWithBaseURL(baseUrl, content, "text/html", "UTF-8", "")
        }
    }
}
