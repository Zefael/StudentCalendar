package com.blueamber.studentcalendar.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class TasksCalendar(val date: Date,
                         val titre: String,
                         val type: String,
                         val typeOfSource: TypeOfSource,
                         val colorTask: Int,
                         val hourStart: String,
                         val hourEnd: String,
                         val professors: String,
                         val rooms: String,
                         var group: String,
                         val notes: String) : Parcelable, Comparable<TasksCalendar> {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override operator fun compareTo(other: TasksCalendar): Int {
        val dateCompared = dateCompareTo(other.date)
        return if (dateCompared == 0) {
            val h1 = Integer.valueOf(this.hourStart.substring(0, 2))
            val h2 = Integer.valueOf(other.hourStart.substring(0, 2))
            Integer.compare(h1, h2)
        } else {
            dateCompared
        }
    }

    fun dateCompareTo(date: Date): Int = this.date.time.compareTo(date.time)
}