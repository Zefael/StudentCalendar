package com.blueamber.studentcalendar.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emas.mondial.ui.main.Back
import com.emas.mondial.ui.main.LoadFragment
import com.emas.mondial.ui.main.NavigationState

class MainViewModel : ViewModel() {


    val navigation = MutableLiveData<NavigationState>()
    val statusBarColor = MutableLiveData<Int>()
    val statusBarTitle = MutableLiveData<String>()
    val statusBarOptionIcon = MutableLiveData<Int>()
    val refreshNeeded = MutableLiveData<Boolean>()

    fun back() {
        navigation.value = Back
    }

    fun loadFragment(fragment: NavigationFragment) {
        navigation.value = LoadFragment(fragment)
    }

    fun setStatusBarColor(color: Int) {
        statusBarColor.value = color
    }

    fun setStatusBarTitle(title: String) {
        statusBarTitle.value = title
    }
}