package com.example.zohosurvey.viewmodelfactorys

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zohosurvey.viewmodels.LinkViewModel

class LinkFactory (private val context: Context): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LinkViewModel::class.java)){
            return LinkViewModel(context) as T
        } else {
            throw IllegalArgumentException("Unknown class")
        }
    }
}