package com.example.myapplication.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "character")
data class Characters (
    @PrimaryKey(autoGenerate = true)
    val char_id: Int,
    val char_name: String,
    val char_alias: String,
    val char_scene: String,
    val bio: String,
    val projectID: Int,
    val profile_image: Uri?,
        ):Parcelable