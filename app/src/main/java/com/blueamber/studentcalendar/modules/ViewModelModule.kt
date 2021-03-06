package com.blueamber.studentcalendar.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blueamber.studentcalendar.modules.viewmodel.ViewModelFactory
import com.blueamber.studentcalendar.modules.viewmodel.ViewModelKey
import com.blueamber.studentcalendar.ui.addtask.AddTaskViewModel
import com.blueamber.studentcalendar.ui.calendartasks.CalendarTasksViewModel
import com.blueamber.studentcalendar.ui.settings.SettingsViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTaskViewModel::class)
    abstract fun bindAddTaskViewModel(viewModel: AddTaskViewModel): ViewModel

}