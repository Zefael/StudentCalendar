package com.blueamber.studentcalendar.models

import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Work(val titre: String,
                val type: String,
                val typeOfSource: TypeOfSource,
                val hourStart: String,
                val hourEnd: String,
                val professors: String,
                val rooms: String,
                val group: String,
                val notes: String): Parcelable, Comparable<Work> {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override operator fun compareTo(other: Work): Int {
        val h1 = Integer.valueOf(this.hourStart.substring(0, 2))
        val h2 = Integer.valueOf(other.hourStart.substring(0, 2))
        return Integer.compare(h1, h2)
    }
}