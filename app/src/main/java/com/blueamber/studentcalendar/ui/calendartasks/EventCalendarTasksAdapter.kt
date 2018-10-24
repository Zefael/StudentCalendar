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

                var dw = R.drawable.card_event_default
                when (it.type) {
                    "Cours" -> dw = getCardLesson(it.typeOfSource)
                    "TD" -> dw = getCardTD(it.typeOfSource)
                    "Cours+TD" -> dw = getCardLessonAndTD(it.typeOfSource)
                }

                val drawable = context.getDrawable(dw)
                eventCard.background = drawable
            }
        }

        private fun getCardLesson(typeOfSource: TypeOfSource): Int {
            when (typeOfSource) {
                TypeOfSource.CELCAT -> return R.drawable.card_event_cours_celcat
                TypeOfSource.OTHER -> return R.drawable.card_event_cours_other
            }
        }

        private fun getCardTD(typeOfSource: TypeOfSource): Int {
            when (typeOfSource) {
                TypeOfSource.CELCAT -> return R.drawable.card_event_td_celcat
                TypeOfSource.OTHER -> return R.drawable.card_event_td_other
            }
        }

        private fun getCardLessonAndTD(typeOfSource: TypeOfSource): Int {
            when (typeOfSource) {
                TypeOfSource.CELCAT -> return R.drawable.card_event_cours_td_celcat
                TypeOfSource.OTHER -> return R.drawable.card_event_cours_td_other
            }
        }

        private fun getWork() = data as Work
    }
}