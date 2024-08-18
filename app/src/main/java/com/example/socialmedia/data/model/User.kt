package com.example.socialmedia.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user",indices = [Index(value = ["email"],
    unique = true)])
data class User(
    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "dob")
    val dateOfBirth: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "role")
    val role: String = "user".toString(),


    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)