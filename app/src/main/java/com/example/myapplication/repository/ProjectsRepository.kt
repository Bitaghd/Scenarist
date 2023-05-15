package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.ProjectsDao
import com.example.myapplication.model.Projects

class ProjectsRepository(private val projectsDao: ProjectsDao) {
    val readAllProjects : LiveData<List<Projects>> = projectsDao.readAllProjects()
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

    fun findProjectById(id:Int) {

        //val record: LiveData<Projects> = projectsDao.findProjectById(id)
        //return record
        //getRecord = projectsDao.findProjectById(id)
        projectsDao.findProjectById(id)
    }

}