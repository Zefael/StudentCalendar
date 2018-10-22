package com.blueamber.studentcalendar.ui.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        preInit()

        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupData()
    }

    protected abstract fun getLayoutId(): Int
    protected open fun preInit() {}
    protected open fun setupViews() {}
    protected open fun setupData() {}

}