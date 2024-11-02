package com.example.zohosurvey.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.database.DatabaseLogin
import com.example.zohosurvey.database.getLoginDatabase
import com.example.zohosurvey.util.SharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainDrawerViewModel(context: Context): ViewModel() {
    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + job)

    private val database = getLoginDatabase(context.applicationContext as Application)

    private val sharedPreferences = SharedPreferencesManager(context)

    private val _email = MutableLiveData<String?>()
    val email: LiveData<String?> get() = _email

    private val _userList = MutableLiveData<List<DatabaseLogin>?>()
    val userList: LiveData<List<DatabaseLogin>?> get() = _userList

    init {
        fetchUserDetails() // Fetch user details when ViewModel is initialized
    }

    private fun fetchUserDetails() {
        viewModelScope.launch {
            val userDetails = database.loginDao.getAllDetails()
            _userList.postValue(userDetails)
            val (emailTemp, _) = sharedPreferences.getUserDetails()
            val user = userDetails?.find { it.email == emailTemp }
            _email.postValue(user?.email) // Update LiveData with the email
        }
    }

    fun logout(){
        sharedPreferences.clearData()
    }
}