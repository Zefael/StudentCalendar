package com.blueamber.studentcalendar

import com.blueamber.studentcalendar.modules.NetworkModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class
])
interface AppComponent {

    fun inject(app: StudentCalendarApp)

}