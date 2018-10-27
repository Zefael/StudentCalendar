package com.blueamber.studentcalendar.ui.calendartasks

import android.view.ViewGroup
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.TypeOfSource
import com.blueamber.studentcalendar.models.Work
import com.blueamber.studentcalendar.ui.base.recycler.BaseHolder
import com.blueamber.studentcalendar.ui.base.recycler.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.event_card.*

class EventCalendarTasksAdapter : BaseRecyclerAdapter<Any>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Any> = EventHolder(parent)

    class EventHolder(parent: ViewGroup) : BaseHolder<Any>(parent, R.layout.event_card) {

        override fun display() {
            getWork().let {
                profs.text = it.professors
                modules.text = it.titre.split("\n")[0]
                horaire.text = context.resources.getString(R.string.hours_start_end, it.hourStart, it.hourEnd)
                lieu.text = it.rooms
                if (!it.notes.isEmpty()) {
                    notes.text = context.resources.getString(R.string.notes_event, it.notes)
                }

                val color = when (it.type) {
                    "Cours" -> getCardLesson(it.typeOfSource)
                    "TD" -> getCardTD(it.typeOfSource)
                    "Cours+TD" -> getCardLessonAndTD(it.typeOfSource)
                    else -> R.color.grey
                }

                eventCard.setBackgroundColor(color)
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

        private fun getWork() = data as Work
    }
}