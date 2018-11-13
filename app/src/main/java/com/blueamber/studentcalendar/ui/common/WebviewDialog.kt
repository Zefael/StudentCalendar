package com.blueamber.studentcalendar.ui.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.ui.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_webview.*

class WebviewDialog : BaseDialog() {

    companion object {
        private const val ARG_URL = "url"

        fun show(fragmentManager: FragmentManager, url: String) {
            val dialog = WebviewDialog()
            val args = Bundle()
            args.putString(ARG_URL, url)
            dialog.arguments = args

            dialog.show(fragmentManager, "WebviewDialog")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialog)
    }

    override fun getLayoutId() = R.layout.dialog_webview

    @SuppressLint("SetJavaScriptEnabled")
    override fun setupViews() {
        webviewClose.setOnClickListener { dismiss() }
        webviewContent.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            displayZoomControls = false
        }
    }

    override fun setupData() {
        val url = arguments!!.getString(ARG_URL)
        webviewContent.loadUrl(url)
        webviewContent.reload()
    }

}