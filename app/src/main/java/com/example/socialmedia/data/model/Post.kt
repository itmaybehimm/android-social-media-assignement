package com.example.socialmedia.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
@Entity(
    tableName = "post",
    indices = [Index(value = ["user_id"])],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Post(
    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,  // New field for storing image URL

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
