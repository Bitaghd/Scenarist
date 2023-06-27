package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProjectsDatabase
import com.example.myapplication.model.Characters
import com.example.myapplication.model.Details
import com.example.myapplication.model.Location
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

    fun setCurrentItem(projects: Projects) {
        currentProject.value = projects
//        projectID = projects.id
        //currentProject.postValue(projects)
    }

    fun getScenesInProject(projectID: Int): LiveData<List<Scene>> {
            return repository.getProjectWithScenes(projectID)
    }

    // SCENES THINGS

    fun addScene(scene: Scene){
        viewModelScope.launch(Dispatchers.IO){
            repository.addScene(scene)
        }
    }
    fun updateScene(scene: Scene){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateScene(scene)
        }
    }
    fun deleteScene(scene: Scene){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteScene(scene)
        }
    }

    // Locations coroutines

    fun addLocation(location: Location){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLocation(location)
        }
    }

    fun updateLocation(location: Location){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLocation(location)
        }
    }

    fun deleteLocation(location: Location){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLocation(location)
        }
    }

    fun getLocationsInProject(projectID: Int): LiveData<List<Location>> {
        return repository.getAllLocations(projectID)
    }

    fun addCharacter(characters: Characters){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCharacter(characters)
        }
    }
    fun deleteCharacter(characters: Characters){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCharacter(characters)
        }
    }

    fun updateCharacter(characters: Characters){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCharacter(characters)
        }
    }

    fun getCharactersInProject(projectID: Int): LiveData<List<Characters>> {
        return repository.getAllCharacters(projectID)
    }

    fun addDetails(details: Details){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDetails(details)
        }
    }

    fun deleteDetails(details: Details){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteDetails(details)
        }
    }
    fun updateDetails(details: Details){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateDetails(details)
        }
    }
    fun getDetailsInProject(projectID: Int): LiveData<List<Details>> {
        return repository.getAllDetails(projectID)
    }


}
