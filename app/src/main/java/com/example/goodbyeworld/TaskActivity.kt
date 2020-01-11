package com.example.goodbyeworld

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val task = intent.getSerializableExtra(TaskFragment.TASK_KEY) as? Task
        editText.setText(task?.title ?: "")
        editText.setText(task?.description ?: "")

        editButton.setOnClickListener {
            val newTask = Task(id = task?.id ?: UUID.randomUUID().toString(), title = editText.text.toString(), description = editText.text.toString())
            intent.putExtra(TaskFragment.TASK_KEY, newTask)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }


}