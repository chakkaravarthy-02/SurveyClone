package com.example.zohosurvey.viewmodels

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import com.example.zohosurvey.database.DatabaseLogin
import com.example.zohosurvey.database.getLoginDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + job)

    private val _insertStatus = MutableSharedFlow<Boolean>()
    val insertStatus: SharedFlow<Boolean> = _insertStatus

    fun insertUserDetails(email: String, password: String, phoneNumber: String) {
        val user = DatabaseLogin(email = email, password = password, phoneNumber =  phoneNumber)
        viewModelScope.launch {
            try {
                getLoginDatabase(app).loginDao.insert(user)
                _insertStatus.emit(true)
            } catch (e: SQLiteConstraintException) {
                println("insert error: $e")
                _insertStatus.emit(false)
            }
        }
    }
}