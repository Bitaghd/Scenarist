package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Characters
import com.example.myapplication.model.Details
import com.example.myapplication.model.Location
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene

@Dao
interface ProjectsDao {

    // Project related queries
    @Insert
    suspend fun addProjects(projects: Projects)
    @Delete
    suspend fun deleteProject(projects: Projects)
    //TODO: Manage cascade project deletion
    @Query("DELETE FROM projects")
    suspend fun deleteAllProjects()
    @Query("SELECT * FROM projects WHERE projects.id = :id")
    fun findProjectById(id: Int): LiveData<Projects>
    @Update
    suspend fun updateProject(projects: Projects)
    @Query("SELECT * FROM projects")
    fun readAllProjects(): LiveData<List<Projects>>

    // Scenes related queries

    @Insert
    suspend fun addScene(scene: Scene)

    @Delete
    suspend fun deleteScene(scene: Scene)

    @Query("DELETE FROM scene")
    suspend fun deleteAllScenes()

    @Update
    suspend fun updateScene(scene: Scene)

    @Query("SELECT * FROM scene WHERE projectID = :projectID")
    fun getAllScenes(projectID: Int): LiveData<List<Scene>>
//    @Transaction
//    @Query("SELECT * FROM scene WHERE projectID = :projectID")
//    fun getScenesInProject(projectID: Int): LiveData<List<ProjectWithScenes>>

    // Locations related queries

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)

    @Query("DELETE FROM location")
    suspend fun deleteAllLocations()

    @Update
    suspend fun updateLocation(location: Location)

    @Query("SELECT * FROM location WHERE projectID = :projectID")
    fun getAllLocations(projectID: Int): LiveData<List<Location>>

    //Character
    @Insert
    suspend fun addCharacter(characters: Characters)

    @Delete
    suspend fun deleteCharacter(characters: Characters)

    @Update
    suspend fun updateCharacter(characters: Characters)

    @Query("SELECT * FROM character WHERE projectID = :projectID")
    fun getAllCharacters(projectID: Int): LiveData<List<Characters>>

    //Details
    @Insert
    suspend fun addDetails(details: Details)

    @Delete
    suspend fun deleteDetails(details: Details)

    @Update
    suspend fun updateDetails(details: Details)

    @Query("SELECT * FROM details WHERE projectID = :projectID")
    fun getAllDetails(projectID: Int):LiveData<List<Details>>
}