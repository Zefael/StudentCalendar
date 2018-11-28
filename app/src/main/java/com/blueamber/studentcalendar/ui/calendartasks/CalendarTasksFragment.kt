package com.blueamber.studentcalendar.ui.calendartasks

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.tools.DateUtil
import com.blueamber.studentcalendar.ui.NavigationFragment
import kotlinx.android.synthetic.main.calendar_tasks_fragment.*

class CalendarTasksFragment : NavigationFragment() {

    private lateinit var viewModel: CalendarTasksViewModel
    private lateinit var adapter: CalendarTasksAdapter

    override fun getLayoutId() = R.layout.calendar_tasks_fragment

    override fun setupViewModels() {
        super.setupViewModels()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CalendarTasksViewModel::class.java)
    }

    override fun setupViews() {
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    override fun setupObservers() {
        viewModel.dataDownloaded.observe(this,
            Observer<List<TasksCalendar>> { it ->
                val itFilter = it?.filter { (DateUtil.isToday(it.date.time) && DateUtil.isNowBeforeToTasks(it.hourEnd)) || !DateUtil.isToday(it.date.time) }
                itFilter?.let {
                    adapter.update(it, true)
                    tasks_planing.visibility = View.VISIBLE}
            })
        viewModel.titleToolBar.observe(this,
            Observer { it -> it?.let { applyStatusBarTitle(it) } })
        mainViewModel.refreshNeeded.observe(this,
            Observer<Boolean> {
                tasks_planing.visibility = View.GONE
                viewModel.downloadCalendars()
            })
    }

    override fun setupData() {
        viewModel.downloadCalendars()
        val manager = LinearLayoutManager(requireContext())
        adapter = CalendarTasksAdapter(manager, viewModel)
        tasks_planing.layoutManager = manager
        tasks_planing.adapter = adapter
    }
}
