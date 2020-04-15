package com.example.cz_trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cz_trivia.adapter.RecyclerviewAdapter
import com.example.cz_trivia.databinding.ActivityMainBinding
import com.example.cz_trivia.db.Student
import com.example.cz_trivia.db.StudentDAO
import com.example.cz_trivia.db.StudentDatabase
import com.example.cz_trivia.repository.Repository
import com.example.cz_trivia.viewmodel.StudentViewModel
import com.example.cz_trivia.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var studentViewModel: StudentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao:StudentDAO = StudentDatabase.invoke(application).studentDAO
        val repository = Repository(dao)
        val factory = StudentViewModelFactory(repository)
        studentViewModel = ViewModelProvider(this,factory).get(StudentViewModel::class.java)
        binding.viewmodel = studentViewModel
        binding.lifecycleOwner = this

//        displayStudents()
        initRecyclerview()

        studentViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun initRecyclerview(){
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        displayStudents()
    }

    private fun displayStudents(){
       studentViewModel.students.observe(this, Observer {
           Log.d("mytag",it.toString())
//           binding.recyclerview.adapter = RecyclerviewAdapter(it)
           binding.recyclerview.adapter = RecyclerviewAdapter(it,{selectItem:Student->listItemClicked(selectItem)})
       })
    }

    private fun listItemClicked(student: Student){
        //Toast.makeText(this,"select name is ${student.name}",Toast.LENGTH_LONG).show()
        studentViewModel.initUpdateAndDelete(student)
    }








}
