package com.example.myapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "projects")
data class Projects (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val pr_name: String,
    val author_name: String,
    val date: Int
        ):Parcelable
