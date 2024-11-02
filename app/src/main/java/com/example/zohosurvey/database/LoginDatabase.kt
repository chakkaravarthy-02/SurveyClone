package com.example.zohosurvey.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseLogin::class], version = 5, exportSchema = false)
abstract class LoginDatabase: RoomDatabase(){
    abstract val loginDao: LoginDao
}

private lateinit var INSTANCE: LoginDatabase

fun getLoginDatabase(context: Context): LoginDatabase{
    synchronized(LoginDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                LoginDatabase::class.java,
                "Login")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}