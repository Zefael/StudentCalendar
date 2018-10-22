package com.blueamber.studentcalendar.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blueamber.studentcalendar.ui.base.ActionLiveData
import com.emas.mondial.ui.main.Back
import com.emas.mondial.ui.main.LoadFragment
import com.emas.mondial.ui.main.NavigationState

class MainViewModel : ViewModel() {

    val navigation = ActionLiveData<NavigationState>()
    val statusBarColor = MutableLiveData<Int>()

    fun back() {
        navigation.value = Back
    }

    fun loadFragment(fragment: NavigationFragment) {
        navigation.value = LoadFragment(fragment)
    }

    fun setStatusBarColor(color: Int) {
        statusBarColor.value = color
    }

}