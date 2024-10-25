package com.example.zohosurvey.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.util.SharedPreferencesManager

class MainDrawerViewModel(context: Context): ViewModel() {
    private val sharedPreferences = SharedPreferencesManager(context)

    fun logout(){
        sharedPreferences.clearData()
    }
}