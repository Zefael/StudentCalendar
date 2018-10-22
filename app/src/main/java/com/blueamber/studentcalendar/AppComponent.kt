package com.blueamber.studentcalendar

import com.blueamber.studentcalendar.modules.*
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    DaoModule::class,
    FragmentModule::class,
    ViewModelModule::class,
    NetworkModule::class
])
interface AppComponent {

    fun inject(app: StudentCalendarApp)

}