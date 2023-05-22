package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.ProjectsDao
import com.example.myapplication.model.Location
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene
import com.example.myapplication.model.relations.ProjectWithScenes

class ProjectsRepository(private val projectsDao: ProjectsDao) {
    val readAllProjects : LiveData<List<Projects>> = projectsDao.readAllProjects()
    //val getProjectWithScenes: LiveData<List<ProjectWithScenes>> = projectsDao.getScenesInProject()
    //val findProjectByID: LiveData<Projects> = projectsDao.findProjectById(id)
    //val getRecord: LiveData<Projects>

    suspend fun addProjects(projects: Projects){
        projectsDao.addProjects(projects)
    }

    suspend fun updateProject(projects: Projects){
        projectsDao.updateProject(projects)
    }
    suspend fun deleteProject(projects: Projects){
        projectsDao.deleteProject(projects)
    }

    suspend fun deleteAllProjects(){
        projectsDao.deleteAllProjects()
    }

    fun findProjectById(id:Int): LiveData<Projects> {

        //val record: LiveData<Projects> = projectsDao.findProjectById(id)
        //return record
        //getRecord = projectsDao.findProjectById(id)
        return projectsDao.findProjectById(id)
    }

    fun getProjectWithScenes(projectID: Int): LiveData<List<Scene>>{
        return projectsDao.getAllScenes(projectID)
        //return projectsDao.getScenesInProject(projectID)
    }

    suspend fun addScene(scene: Scene){
        projectsDao.addScene(scene)
    }

    suspend fun updateScene(scene: Scene){
        projectsDao.updateScene(scene)
    }

    suspend fun deleteScene(scene: Scene){
        projectsDao.deleteScene(scene)
    }

    suspend fun deleteAllScenes(){
        projectsDao.deleteAllScenes()
    }

    // Locations
    fun getAllLocations(projectID: Int): LiveData<List<Location>> {
        return projectsDao.getAllLocations(projectID)
    }

    suspend fun addLocation(location: Location){
        projectsDao.addLocation(location)
    }

    suspend fun updateLocation(location: Location){
        projectsDao.updateLocation(location)
    }

    suspend fun deleteLocation(location: Location){
        projectsDao.deleteLocation(location)
    }
    suspend fun deleteAllLocations(){
        projectsDao.deleteAllLocations()
    }


}