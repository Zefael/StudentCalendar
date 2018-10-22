package com.blueamber.studentcalendar.modules

import com.blueamber.studentcalendar.ui.StudentCalendarActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): StudentCalendarActivity

}