package com.blueamber.studentcalendar.ui.calendartasks

import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.Snackbar
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.ui.NavigationFragment
import kotlinx.android.synthetic.main.calendar_tasks_fragment.*

class CalendarTasksFragment : NavigationFragment() {

    companion object {
        fun newInstance() = CalendarTasksFragment()
    }

    private lateinit var viewModel: CalendarTasksViewModel

    override fun getLayoutId() = R.layout.calendar_tasks_fragment

    override fun setupViewModels() {
        super.setupViewModels()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CalendarTasksViewModel::class.java)
    }

    override fun setupViews() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun setupData() {
        viewModel.downloadCalendars()
    }
}
