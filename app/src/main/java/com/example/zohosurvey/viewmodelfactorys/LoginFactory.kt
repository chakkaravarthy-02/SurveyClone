package com.example.zohosurvey.viewmodelfactorys

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zohosurvey.viewmodels.LoginViewModel

class LoginFactory(private val app: Application,private val context: Context): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(app, context) as T
        } else {
            throw IllegalArgumentException("Unknown class")
        }
    }
}