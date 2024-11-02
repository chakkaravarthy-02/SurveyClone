package com.example.zohosurvey.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["email"], unique = true)]
)
data class DatabaseLogin(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val email: String,
    val password: String,
    val phoneNumber: String
)