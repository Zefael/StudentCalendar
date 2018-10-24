package com.blueamber.studentcalendar.ui.calendartasks

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Day
import com.blueamber.studentcalendar.ui.base.recycler.BaseHolder
import com.blueamber.studentcalendar.ui.base.recycler.BaseRecyclerAdapter
import com.enterprise.baseproject.util.DateUtil
import kotlinx.android.synthetic.main.item_tasks_by_day.*

class CalendarTasksAdapter(
    private val layoutManager: LinearLayoutManager,
    data: List<Day>,
    private val calendarTasksViewModel: CalendarTasksViewModel
) : BaseRecyclerAdapter<Day>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Day> =
        DayHolder(layoutManager, parent, calendarTasksViewModel)

    class DayHolder(
        private val layoutManager: LinearLayoutManager,
        parent: ViewGroup,
        private val viewModel: CalendarTasksViewModel
    ) : BaseHolder<Day>(parent, R.layout.item_tasks_by_day) {

        override fun display() {
            /*data?.let {
                // Change title of Toolbar
                val itemFirstVisible = layoutManager.findFirstVisibleItemPosition() + 1
                if (itemFirstVisible >= 0) {
                    viewModel.setToolbarTitle(DateUtil.monthOfDate(it.get(itemFirstVisible).date))
                }
                simple_separation.visibility = View.VISIBLE
                month_separation.visibility = View.GONE
                // Set item
                jour.text = DateUtil.dayOfMonth(it.date.time).toString()
                jour_semaine.text = DateUtil.dayOfWeek(it.date.time)
                // Set recycler
                eventDay.setHasFixedSize(true)
                eventDay.layoutManager = LinearLayoutManager(context)
                val adapter = EventCalendarTasksAdapter(it.works)
                eventDay.adapter = adapter
                // Change separation item
                if ( != i +1) {
                    val dayFuture = it.get(i + 1)
                    val nowMonth = DateUtil.monthOfDate(it.getDate())
                    val nextMonth = DateUtil.monthOfDate(dayFuture.getDate())
                    if (nowMonth != nextMonth) {
                        simple_separation.visibility = View.GONE
                        month_separation.visibility = View.VISIBLE
                        month.text = nextMonth
                    }
                } else {
                    simple_separation.visibility = View.GONE
                }
            }*/
        }
    }
}