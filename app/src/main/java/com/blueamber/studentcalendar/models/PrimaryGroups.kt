package com.blueamber.studentcalendar.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class PrimaryGroups(
    @PrimaryKey val originalPrimaryGroup: String,
    val newPrimaryGroup: String,
    val visibility: Boolean
) : Parcelable