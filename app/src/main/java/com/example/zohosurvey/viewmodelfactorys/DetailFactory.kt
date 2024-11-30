package com.example.zohosurvey.viewmodelfactorys

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zohosurvey.viewmodels.DetailViewModel

class DetailFactory(private val context: Context): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(context) as T
        } else {
            throw IllegalArgumentException("Unknown class")
        }
    }
}