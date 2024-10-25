package com.example.zohosurvey.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseLogin(
    @PrimaryKey
    @ColumnInfo val email: String,
    @ColumnInfo val password: String,
    @ColumnInfo val phoneNumber: String
)