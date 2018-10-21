package com.blueamber.studentcalendar.ui.calendartasks

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blueamber.studentcalendar.R
import kotlinx.android.synthetic.main.calendar_tasks_fragment.*

class CalendarTasksFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarTasksFragment()
    }

    private lateinit var viewModel: CalendarTasksViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calendar_tasks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        viewModel = ViewModelProviders.of(this).get(CalendarTasksViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
