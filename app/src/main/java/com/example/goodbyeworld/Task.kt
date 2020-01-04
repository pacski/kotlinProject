package com.example.goodbyeworld

import java.io.Serializable

data class Task(val id: String, val title: String, val description: String = "fijhigh") : Serializable {


}