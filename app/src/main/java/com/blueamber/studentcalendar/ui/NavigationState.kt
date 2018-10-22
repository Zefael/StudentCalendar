package com.emas.mondial.ui.main

import com.blueamber.studentcalendar.ui.NavigationFragment

sealed class NavigationState
    object Back : NavigationState()
    data class LoadFragment(val fragment: NavigationFragment) : NavigationState()