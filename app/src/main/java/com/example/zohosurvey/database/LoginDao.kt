package com.example.zohosurvey.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoginDao{
    @Insert
    fun insert(vararg login: DatabaseLogin)

    @Query("Select * from databaselogin")
    fun getAllDetails(): List<DatabaseLogin>?
}