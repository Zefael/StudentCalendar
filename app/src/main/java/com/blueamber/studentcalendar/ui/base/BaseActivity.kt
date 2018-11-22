package com.blueamber.studentcalendar.ui.base

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.blueamber.studentcalendar.modules.Injectable
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(this is Injectable) {
            AndroidInjection.inject(this)
        }

        removeStatusBarBackground()

        super.onCreate(savedInstanceState)

        preInit()
        setContentView(getLayoutId())
        setupViewModels()
        setupViews()
        setupObservers()
        setupData()
    }

    private fun removeStatusBarBackground() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
    }

    protected abstract fun getLayoutId() : Int
    protected open fun preInit() {}
    protected open fun setupViewModels() {}
    protected open fun setupViews() {}
    protected open fun setupObservers() {}
    protected open fun setupData() {}

}