package com.blueamber.studentcalendar.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Day(val date: Date, var works: List<Work>) : Parcelable, Comparable<Day> {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun compareTo(other: Day): Int = date.time.compareTo(other.date.time)
}