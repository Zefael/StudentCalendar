package com.blueamber.studentcalendar.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApp(): Application = application

    @Provides
    fun provideContext(): Context = application

}