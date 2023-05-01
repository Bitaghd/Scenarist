package com.example.myapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Scene")
data class Scene (
        @PrimaryKey(autoGenerate = true)
        val scene_id: Int,
        val scene_name: String,
        val location: String,
        val desc: String
        ):Parcelable