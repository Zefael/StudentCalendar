package com.blueamber.studentcalendar.ui.calendartasks

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.tools.DateUtil
import com.blueamber.studentcalendar.ui.NavigationFragment
import kotlinx.android.synthetic.main.calendar_tasks_fragment.*

class CalendarTasksFragment : NavigationFragment() {

    companion object {
        fun newInstance() = CalendarTasksFragment()
    }

    private lateinit var viewModel: CalendarTasksViewModel
    private lateinit var adapter: CalendarTasksAdapter

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

    override fun setupObservers() {
        viewModel.dataDownloaded.observe(this,
            Observer<List<TasksCalendar>> { it ->
                val itFilter = it?.filter { (DateUtil.isToday(it.date.time) && DateUtil.isNowBeforeToTasks(it.hourEnd)) || !DateUtil.isToday(it.date.time) }
                itFilter?.let { adapter.update(it, true) }
            })
        viewModel.titleToolBar.observe(this,
            Observer { it -> it?.let { applyStatusBarTitle(it) } })
    }

    override fun setupData() {
        viewModel.downloadCalendars()
        val manager = LinearLayoutManager(requireContext())
        adapter = CalendarTasksAdapter(manager, viewModel)
        tasks_planing.layoutManager = manager
        tasks_planing.adapter = adapter
    }
}
