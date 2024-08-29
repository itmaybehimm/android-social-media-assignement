package com.example.socialmedia.data.model

import Converters
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.text.SimpleDateFormat

import java.util.Date
import java.util.Locale


@Entity(
    tableName = "user",
    indices = [Index(value = ["email"], unique = true)]
)
@TypeConverters(Converters::class)
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

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

