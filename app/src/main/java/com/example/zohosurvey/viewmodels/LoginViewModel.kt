package com.example.zohosurvey.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.zohosurvey.database.DatabaseLogin
import com.example.zohosurvey.database.getLoginDatabase
import com.example.zohosurvey.util.SharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(app: Application, context: Context) : AndroidViewModel(app){

    private val _isValidPassword = MutableStateFlow<Boolean?>(null)
    val isValidPassword: StateFlow<Boolean?> get() = _isValidPassword

    private val _isValidEmail = MutableStateFlow<Boolean?>(null)
    val isValidEmail: StateFlow<Boolean?> get() = _isValidEmail

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + job)

    private val database = getLoginDatabase(app)
    private val preferencesManager = SharedPreferencesManager(context)

    private var list : List<DatabaseLogin>?= listOf()

    init {
        viewModelScope.launch {
            list = database.loginDao.getAllDetails()
            println(list)
        }
    }

    fun isEmailValid(email: String){
        val currentEmail = list?.find { it.email == email }
        _isValidEmail.value = currentEmail?.email == email
    }

    fun isPasswordValid(password: String){
        val currentPassword = list?.find { it.password == password }
        _isValidPassword.value = currentPassword?.password == password
    }

    fun savePreference(emailText: String) {
        preferencesManager.saveUserDetail(emailText, System.currentTimeMillis())
    }
}