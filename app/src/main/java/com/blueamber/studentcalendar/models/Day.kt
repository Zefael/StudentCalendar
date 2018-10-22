package com.blueamber.studentcalendar.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Day(val date: Date, val works: List<Work>) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}