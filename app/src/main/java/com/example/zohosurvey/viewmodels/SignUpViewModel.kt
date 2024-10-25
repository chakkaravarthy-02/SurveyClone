package com.example.zohosurvey.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.zohosurvey.database.DatabaseLogin
import com.example.zohosurvey.database.getLoginDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SignUpViewModel(private val app: Application): AndroidViewModel(app){

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + job)

    fun insertUserDetails(email: String, password: String, phoneNumber: String):Boolean{
        val user = DatabaseLogin(email,password,phoneNumber)
        try{
            viewModelScope.launch {
                getLoginDatabase(app).loginDao.insert(user)
            }
            return true
        }catch(e:Exception){
            println("insert error: $e")
        }
        return false
    }
}