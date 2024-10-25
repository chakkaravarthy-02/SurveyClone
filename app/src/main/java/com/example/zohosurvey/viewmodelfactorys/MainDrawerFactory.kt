package com.example.zohosurvey.viewmodelfactorys

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zohosurvey.viewmodels.MainDrawerViewModel

class MainDrawerFactory(private val context: Context): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainDrawerViewModel::class.java)){
            return MainDrawerViewModel(context) as T
        }else{
            throw IllegalArgumentException("Unknown class")
        }
    }
}