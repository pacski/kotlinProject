package com.example.goodbyeworld;

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

public class TaskFragment : Fragment() {
    private val adapter = TaskAdapter()
    private val tasksViewModel by lazy { ViewModelProvider(this).get(TasksViewModel::class.java) }
    private val coroutineScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TaskRecyclerView.adapter = adapter
        TaskRecyclerView.layoutManager = LinearLayoutManager(activity)


        floatingActionButton2.setOnClickListener {
            val task = Task(id = UUID.randomUUID().toString(), title = "New Task !")
            tasksViewModel.addTask(task)
        }


        adapter.onDeleteClickListener = { task ->
            tasksViewModel.deleteTask(task)
        }


        adapter.onEditClickListener = { task ->
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("task_key", task)
            startActivityForResult(intent, EDIT_TASK_REQUEST)
        }


        // Inflate the layout for this fragment
        tasksViewModel.tasks.observe(this, Observer { newList ->
            adapter.list = newList.orEmpty()
        })

    }

    override fun onResume() {
        super.onResume()
        coroutineScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            nameView.text = "${userInfo.firstName} ${userInfo.lastName}"
        }
        tasksViewModel.loadTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if() {
            val task = intent!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            tasksViewModel.addTask(task)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val ADD_TASK_REQUEST = 666
    }
}
