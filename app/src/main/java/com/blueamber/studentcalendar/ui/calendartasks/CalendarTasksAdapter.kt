package com.blueamber.studentcalendar.ui.calendartasks

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.models.TypeOfSource
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
            data?.let {
                // Change title of Toolbar
                val itemFirstVisible = layoutManager.findFirstVisibleItemPosition() + 1
                if (itemFirstVisible > 0) {
                    viewModel.setToolbarTitle(DateUtil.monthOfDate(dataList[itemFirstVisible].date.time))
                }
                simple_separation.visibility = View.VISIBLE
                month_separation.visibility = View.GONE
                // Set item
                event_date.text = context.getString(
                    R.string.date_event,
                    DateUtil.dayOfMonth(it.date.time).toString(),
                    DateUtil.dayOfWeek(it.date.time)
                )

                profs.text = it.professors
                modules.text = it.titre.split("\n")[0]
                hours_start_end.text = context.resources.getString(R.string.hours_start_end, it.hourStart, it.hourEnd)
                place.text = it.rooms
                groups.text = it.group
                if (!it.notes.isEmpty()) {
                    notes.visibility = View.VISIBLE
                    notes.text = context.resources.getString(R.string.notes_event, it.notes)
                }

                val color = when (it.type) {
                    "Cours" -> getCardLesson(it.typeOfSource)
                    "TD" -> getCardTD(it.typeOfSource)
                    "Cours+TD" -> getCardLessonAndTD(it.typeOfSource)
                    "TD machine" -> R.color.red
                    else -> R.color.grey
                }

                card.setCardBackgroundColor(ContextCompat.getColor(context, color))

                // Change separation item
                if (dataList.size != layoutPosition + 1) {
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

        private fun getCardLesson(typeOfSource: TypeOfSource): Int {
            return when (typeOfSource) {
                TypeOfSource.CELCAT -> R.color.blue_light
                TypeOfSource.OTHER -> R.color.orange
            }
        }

        private fun getCardTD(typeOfSource: TypeOfSource): Int {
            return when (typeOfSource) {
                TypeOfSource.CELCAT -> R.color.blue
                TypeOfSource.OTHER -> R.color.red
            }
        }

        private fun getCardLessonAndTD(typeOfSource: TypeOfSource): Int {
            return when (typeOfSource) {
                TypeOfSource.CELCAT -> R.color.blue
                TypeOfSource.OTHER -> R.color.red
            }
        }
    }
}