package com.example.zohosurvey.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences",Context.MODE_PRIVATE)

    fun saveUserDetail(email: String, lastLogin: Long){
        sharedPreferences.edit().apply{
            putString("username",email)
            putLong("lastLogin",lastLogin)
            apply()
        }
    }

    fun getUserDetails(): Pair<String?,Long>{
        val userName = sharedPreferences.getString("username",null)
        val lastLogin = sharedPreferences.getLong("lastLogin",0)

        return Pair(userName,lastLogin)
    }

    fun getUser(): String?{
        return sharedPreferences.getString("username",null)
    }

    fun clearData(){
        sharedPreferences.edit().clear().apply()
    }
}