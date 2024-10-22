package com.example.zohosurvey.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DepartmentDrawerViewModel: ViewModel() {
    fun changeSecond() {
        _firstDepartment.value = !_firstDepartment.value
        _secondDepartment.value = !_secondDepartment.value
    }

    fun changeFirst() {
        _firstDepartment.value = !_firstDepartment.value
        _secondDepartment.value = !_secondDepartment.value
    }

    private val _firstDepartment = mutableStateOf(true)
    val firstDepartment : State<Boolean>
        get() = _firstDepartment

    private var _secondDepartment = mutableStateOf(false)
    val secondDepartment : State<Boolean>
        get() = _secondDepartment
}