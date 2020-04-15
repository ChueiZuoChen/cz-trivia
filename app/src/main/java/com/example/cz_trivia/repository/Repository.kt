package com.example.cz_trivia.repository

import androidx.lifecycle.LiveData
import com.example.cz_trivia.db.Student
import com.example.cz_trivia.db.StudentDAO

class Repository(private val studentDao: StudentDAO) {
    val allStudent: LiveData<List<Student>> = studentDao.getAllStudent()

    suspend fun insert(student: Student): Long {
        return studentDao.insertStudent(student)
    }

    suspend fun update(student: Student) : Int {
        return studentDao.updateStudent(student)
    }

    suspend fun delete(student: Student) :Int{
        return studentDao.deleteStudent(student)
    }

    suspend fun deleteAll() {
        studentDao.deleteAllStudent()
    }
}