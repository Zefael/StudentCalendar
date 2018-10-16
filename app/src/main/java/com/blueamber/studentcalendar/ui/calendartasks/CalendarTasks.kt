package com.blueamber.studentcalendar.ui.calendartasks

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.blueamber.studentcalendar.R

class CalendarTasks : Fragment() {

    companion object {
        fun newInstance() = CalendarTasks()
    }

    private lateinit var viewModel: CalendarTasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_tasks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarTasksViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
