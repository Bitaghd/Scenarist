package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProjectsDatabase
import com.example.myapplication.repository.ProjectsRepository
import com.example.myapplication.model.Projects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(application:Application): AndroidViewModel(application) {
     val readAllProjects: LiveData<List<Projects>>
    private val repository: ProjectsRepository

    init{
        val projectsDao = ProjectsDatabase.getDatabase(application).projectsDao()
        repository = ProjectsRepository(projectsDao)
        readAllProjects = repository.readAllProjects
    }

    fun addProjects(projects: Projects){
        viewModelScope.launch(Dispatchers.IO) {
        repository.addProjects(projects)
        }
    }

    fun updateProject(projects: Projects){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateProject(projects)
        }
    }
    fun deleteProject(projects: Projects){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProject(projects)
        }
    }

    fun deleteAllProjects(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllProjects()
        }
    }
}