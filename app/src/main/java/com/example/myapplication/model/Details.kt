package com.example.myapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "details")
data class Details (
    @PrimaryKey(autoGenerate = true)
    val details_id: Int,
    val details_link: String,
    val projectID: Int
        ):Parcelable