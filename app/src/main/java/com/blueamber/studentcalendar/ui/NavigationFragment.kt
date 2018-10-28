package com.blueamber.studentcalendar.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.CallSuper
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.ui.base.BaseFragment
import javax.inject.Inject

abstract class NavigationFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var mainViewModel : MainViewModel
    private var statusBarColor : Int? = null

    @CallSuper
    override fun setupViewModels() {
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }

    @CallSuper
    override fun onFragmentShown() {
        applyStatusBarColor()
    }

    fun applyStatusBarColor(color : Int? = statusBarColor) {
        statusBarColor = color
        color?.let { mainViewModel.setStatusBarColor(it) }
    }

    fun applyStatusBarTitle(title: String) = mainViewModel.setStatusBarTitle(title)

    fun applyOrientation(orientation : Int) {
        activity?.let { it.requestedOrientation = orientation }
    }

    fun applyOnDecorView(uiOptions: Int) {
        activity?.window?.decorView?.systemUiVisibility = uiOptions
    }

    fun getDecorViewUi() = activity?.window?.decorView?.systemUiVisibility ?: 0

}