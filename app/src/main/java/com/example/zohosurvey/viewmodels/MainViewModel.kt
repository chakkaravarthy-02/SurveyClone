package com.example.zohosurvey.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.util.SharedPreferencesManager
import java.util.concurrent.TimeUnit

class MainViewModel(context: Context): ViewModel() {
    var selectedOption by mutableStateOf("All")
        private set

    fun updateOptionInFilter(option: String) {
        selectedOption = option
    }

    private val _isLogin = mutableStateOf(false)
    val isLogin: MutableState<Boolean> get() = _isLogin

    private val preferencesManager = SharedPreferencesManager(context)

    fun checkUserLoggedIn(){
        val (username, lastLogin) = preferencesManager.getUserDetails()

        if(username!=null && lastLogin!=0L && System.currentTimeMillis() - lastLogin < TimeUnit.HOURS.toMillis(24)){
            _isLogin.value = true
        }else{
            _isLogin.value = false
        }
    }

    val list: List<String> = listOf("test")
}
