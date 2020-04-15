package com.example.cz_trivia.db


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDAO {

    @Insert
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student): Int

    @Delete
    suspend fun deleteStudent(student: Student): Int

    @Query("SELECT * FROM studnet_detail")
    fun getAllStudent(): LiveData<List<Student>>

    @Query("DELETE FROM studnet_detail")
    suspend fun deleteAllStudent(): Int
}