package com.example.myapplication.model

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val location_id: Int,
    val location_name: String,
    val location_scene: String,
    val projectID: Int,
    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: Uri?,
    @ColumnInfo(defaultValue = "not null")
    val desc: String,
        ):Parcelable{
//    constructor(location_id: Int,location_name: String,location_scene: String,
//    projectID: Int,image: Bitmap) : this()
//    constructor(location_id: Int, location_name: String, location_scene: String,
//            projectID: Int, desc: String) : this(location_id, location_name, location_scene, projectID, null, desc)
}

