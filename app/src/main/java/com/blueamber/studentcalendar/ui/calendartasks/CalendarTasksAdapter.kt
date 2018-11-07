package com.blueamber.studentcalendar.ui.calendartasks

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.tools.DateUtil
import com.blueamber.studentcalendar.ui.base.recycler.BaseHolder
import com.blueamber.studentcalendar.ui.base.recycler.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_tasks_by_day.*

class CalendarTasksAdapter(
    private val layoutManager: LinearLayoutManager,
    private val calendarTasksViewModel: CalendarTasksViewModel
) : BaseRecyclerAdapter<TasksCalendar>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<TasksCalendar> =
        DayHolder(layoutManager, parent, calendarTasksViewModel)


    class DayHolder(
        private val layoutManager: LinearLayoutManager,
        parent: ViewGroup,
        private val viewModel: CalendarTasksViewModel
    ) : BaseHolder<TasksCalendar>(parent, R.layout.item_tasks_by_day) {

        override fun display() {
            // Set recycler
            eventDay.setHasFixedSize(true)
            eventDay.layoutManager = LinearLayoutManager(context)
            val adapter = EventCalendarTasksAdapter()
            eventDay.adapter = adapter

            data?.let {
                // Change title of Toolbar
                val itemFirstVisible = layoutManager.findFirstVisibleItemPosition() + 1
                if (itemFirstVisible > 0) {
                    viewModel.setToolbarTitle(DateUtil.monthOfDate(dataList[itemFirstVisible].date.time))
                }
                simple_separation.visibility = View.VISIBLE
                month_separation.visibility = View.GONE
                // Set item
                jour.text = DateUtil.dayOfMonth(it.date.time).toString()
                jour_semaine.text = DateUtil.dayOfWeek(it.date.time)
                adapter.update(it.works)
                // Change separation item
                if (dataList.size != layoutPosition +1) {
                    val dayFuture = dataList[layoutPosition + 1]
                    val nowMonth = DateUtil.monthOfDate(it.date.time)
                    val nextMonth = DateUtil.monthOfDate(dayFuture.date.time)
                    if (nowMonth != nextMonth) {
                        simple_separation.visibility = View.GONE
                        month_separation.visibility = View.VISIBLE
                        month.text = nextMonth
                    }
                } else {
                    simple_separation.visibility = View.GONE
                }
            }
        }
    }
}