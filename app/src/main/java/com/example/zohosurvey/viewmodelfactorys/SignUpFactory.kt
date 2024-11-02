package com.example.zohosurvey.viewmodelfactorys

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zohosurvey.viewmodels.SignUpViewModel

class SignUpFactory(private val app: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(app) as T
        } else {
            throw IllegalArgumentException("Unknown class")
        }
    }
}