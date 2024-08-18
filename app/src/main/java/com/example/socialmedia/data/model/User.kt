package com.example.socialmedia.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user",indices = [Index(value = ["username"],
    unique = true)])
data class User(
    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "password")
    val password: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)