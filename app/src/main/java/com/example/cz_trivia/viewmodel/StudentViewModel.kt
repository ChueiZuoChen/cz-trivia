package com.example.cz_trivia.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cz_trivia.db.Student
import com.example.cz_trivia.event.Event
import com.example.cz_trivia.repository.Repository
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: Repository) : ViewModel(), Observable {

    val students = repository.allStudent

    private var isUpdateOrDelete = false
    private lateinit var studentToUpdateOrDelete: Student

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearOrDeleteButtonText = MutableLiveData<String>()

    private var statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "save"
        clearOrDeleteButtonText.value = "clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            update(studentToUpdateOrDelete)
            studentToUpdateOrDelete.name = inputName.value!!
            studentToUpdateOrDelete.email = inputEmail.value!!
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Student(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearOrDeleteAll() {
        if (isUpdateOrDelete) {
            delete(studentToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun clearAll() {
        deleteAll()
    }

    fun insert(student: Student) = viewModelScope.launch {
        val newRowId: Long = repository.insert(student)
        if (newRowId > -1) {
            statusMessage.value = Event("Student Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun update(student: Student) = viewModelScope.launch {
        val noOfRows:Int = repository.update(student)
        if (noOfRows>0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            studentToUpdateOrDelete = student
            saveOrUpdateButtonText.value = "Save"
            clearOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Updated Successfully")
        }else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun delete(student: Student) = viewModelScope.launch {
        val noOfRowsDelete:Int = repository.delete(student)
        if (noOfRowsDelete>0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            studentToUpdateOrDelete = student
            saveOrUpdateButtonText.value = "Save"
            clearOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDelete Deleted Successfully")
        }else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun deleteAll() = viewModelScope.launch {
        val noOfDeleteAll = repository.deleteAll()
        statusMessage.value = Event("All Student Deleted Successfully")
    }

    fun initUpdateAndDelete(student: Student) {
        inputName.value = student.name
        inputEmail.value = student.email
        isUpdateOrDelete = true
        studentToUpdateOrDelete = student
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}