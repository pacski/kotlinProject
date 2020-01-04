package com.example.goodbyeworld

import android.util.Log

class TasksRepository {
    private val tasksService = Api.tasksService




    suspend fun loadTasks(): List<Task>? {
        val tasksResponse = tasksService.getTasks()
        Log.e("loadTasks", tasksResponse.toString())
        return if (tasksResponse.isSuccessful) tasksResponse.body() else null
    }


    suspend fun removeTask(id: String): Boolean{
        val response = tasksService.deleteTask(id)
        return response.isSuccessful

    }
    suspend fun createTask(task: Task): Task?{
        val response = tasksService.createTask(task)
        return if (response.isSuccessful) response.body() else null

    }
    suspend fun updateTask(task: Task): Task? {
        val response = tasksService.updateTask(task)
        return if (response.isSuccessful) response.body()  else null
    }

}