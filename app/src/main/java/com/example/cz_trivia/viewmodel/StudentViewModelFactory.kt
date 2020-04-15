package com.example.cz_trivia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cz_trivia.repository.Repository
import java.lang.IllegalArgumentException

class StudentViewModelFactory(private val repository: Repository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)){
            return StudentViewModel(repository) as T
        }else {
            throw IllegalArgumentException("Unknow View Model class")
        }
    }

}