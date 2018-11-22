package com.blueamber.studentcalendar.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Groups(
    @PrimaryKey val originalGroups: String,
    val newGroups: String,
    val visibility: Boolean
) : Parcelable