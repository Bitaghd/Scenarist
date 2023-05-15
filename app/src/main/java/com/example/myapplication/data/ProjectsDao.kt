package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Projects

@Dao
interface ProjectsDao {
    @Insert//(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProjects(projects: Projects)
    @Delete
    suspend fun deleteProject(projects: Projects)

    @Query("DELETE FROM projects")
    suspend fun deleteAllProjects()
    @Query("SELECT * FROM projects WHERE projects.id = :id")
    fun findProjectById(id: Int): LiveData<Projects>
    @Update
    suspend fun updateProject(projects: Projects)
    @Query("SELECT * FROM projects")
    fun readAllProjects(): LiveData<List<Projects>>
}