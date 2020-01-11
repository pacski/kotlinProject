package com.example.goodbyeworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*
import kotlin.properties.Delegates

class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    internal var list: List<Task> by Delegates.observable(emptyList()) {
            _, _, _ -> notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            itemView.task_title.text = task.title
            itemView.task_description.text= task.description
            itemView.imageButton.setOnClickListener { onDeleteClickListener.invoke(task) }
            itemView.button.setOnClickListener { onEditClickListener.invoke(task) }
        }
    }

    var onDeleteClickListener: (Task) -> Unit = { }
    var onEditClickListener: (Task) -> Unit = { }
}

