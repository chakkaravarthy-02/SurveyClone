package com.example.zohosurvey.viewmodelfactorys

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zohosurvey.viewmodels.AnswerViewModel

class AnswerFactory(private val context: Context): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AnswerViewModel::class.java)){
            return AnswerViewModel(context) as T
        }else{
            throw IllegalArgumentException("Unknown class")
        }
    }
}