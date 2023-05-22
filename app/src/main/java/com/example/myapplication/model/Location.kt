package com.example.myapplication.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "location")
data class Location (
    @PrimaryKey(autoGenerate = true)
    val location_id: Int,
    val location_name: String,
    val location_scene: String,
    val projectID: Int,
    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: Bitmap
        ):Parcelable