package com.blueamber.studentcalendar.models

import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Work(@PrimaryKey val id: Int,
                val titre: String,
                val type: String,
                val hourStart: String,
                val hourEnd: String,
                val professors: String,
                val rooms: String,
                val notes: String): Parcelable