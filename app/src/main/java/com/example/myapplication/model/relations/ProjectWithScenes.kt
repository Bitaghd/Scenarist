package com.example.myapplication.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene

data class ProjectWithScenes(
    @Embedded val projects: Projects,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectID"
    )
    val scene: List<Scene>
)