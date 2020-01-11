package com.example.goodbyeworld

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel: ViewModel() {
    val tasks = MutableLiveData<List<Task>?>()
    private val repository = TasksRepository()

    fun loadTasks() {
        viewModelScope.launch {
            val taskList = repository.loadTasks()
            tasks.postValue(taskList)
        }
    }
    fun editTask(task: Task) {
        viewModelScope.launch {
            val newTask = repository.updateTask(task)
            if (newTask != null) {
                val newList = tasks.value.orEmpty().toMutableList()
                val position = newList.indexOfFirst { it.id == newTask.id }
                newList[position] = task
                tasks.postValue(newList)
            }

        }

    }
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            val result = repository.removeTask(task.id)
            if (result){
                val newList = tasks.value.orEmpty().toMutableList()
                newList.remove(task)
                tasks.postValue(newList)
            }
        }

    }
    fun addTask(task: Task) {
        viewModelScope.launch {
            val result = repository.createTask(task)
            if (result != null){
                val newList = tasks.value.orEmpty().toMutableList()
                newList.add(task)
                tasks.postValue(newList)
            }
        }
    }
}