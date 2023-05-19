package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProjectsDatabase
import com.example.myapplication.repository.ProjectsRepository
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(application:Application): AndroidViewModel(application) {
     val readAllProjects: LiveData<List<Projects>>
     val currentProject: MutableLiveData<Projects> = MutableLiveData()

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

    fun findProjectById(id: Int):LiveData<Projects>{
        return repository.findProjectById(id)
    }
    fun setCurrentItem(projects: Projects) {
        currentProject.value = projects
        //currentProject.postValue(projects)
    }

    fun getScenesInProject(projectID: Int): LiveData<List<Scene>> {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getProjectWithScenes(projectID)
//        }
        return repository.getProjectWithScenes(projectID)
    }
    fun getProjectId(): Int {
        return currentProject.value!!.id
    }
}
