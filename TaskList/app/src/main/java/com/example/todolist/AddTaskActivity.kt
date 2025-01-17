package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.db.TaskListDatabase
import kotlinx.android.synthetic.main.add_task_activity.*
import android.widget.RadioGroup
import com.example.todolist.db.TaskData

class AddTaskActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener{


    private lateinit var taskAdapter: TaskAdapter
    private var taskDatabase: TaskListDatabase? = null
    private var priority = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task_activity)
        taskAdapter = TaskAdapter(mutableListOf())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        taskDatabase = TaskListDatabase.getInstance(this)
        radioGroup.setOnCheckedChangeListener(this)

        val addTaskButton = findViewById<Button>(R.id.btnDoneTask)


        val title = intent.getStringExtra("title")
        if (title == null || title == ""){
            btnDoneTask.setOnClickListener{
                val task = TaskData(title_ed.text.toString(), priority)
                task.detail = detail_ed.text.toString()
                taskDatabase!!.getTaskDao().saveTask(task)
                finish()
            }
        }else{
            btnDoneTask.text = getString(R.string.update)
            val tId = intent.getIntExtra("tId", 0)
            title_ed.setText(title)
            btnDoneTask.setOnClickListener {
                val task = TaskData(title_ed.text.toString(), priority, tId)
                task.detail = detail_ed.text.toString()
                taskDatabase!!.getTaskDao().updateTask(task)
                finish()
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if (checkedId == R.id.medium){
            priority = 2
        }else if (checkedId == R.id.high) {
            priority = 3
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        if (item?.itemId == android.R.id.home){
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//        return super.onOptionsItemSelected(item!!)
//    }



}