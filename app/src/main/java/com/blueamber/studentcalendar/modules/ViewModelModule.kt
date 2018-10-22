package com.blueamber.studentcalendar.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.blueamber.studentcalendar.modules.viewmodel.ViewModelFactory
import com.blueamber.studentcalendar.modules.viewmodel.ViewModelKey
import com.blueamber.studentcalendar.ui.calendartasks.CalendarTasksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CalendarTasksViewModel::class)
    abstract fun bindCalendarTasksViewModel(viewModel: CalendarTasksViewModel): ViewModel

}