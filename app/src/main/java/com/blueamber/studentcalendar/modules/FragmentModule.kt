package com.blueamber.studentcalendar.modules

import com.blueamber.studentcalendar.ui.calendartasks.CalendarTasksFragment
import com.blueamber.studentcalendar.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeCalendarTasks(): CalendarTasksFragment

    @ContributesAndroidInjector
    abstract fun contributeSettngs(): SettingsFragment

}