package com.example.socialmedia.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "comment",
    indices = [Index(value = ["post_id"])],
    foreignKeys = [ForeignKey(
        entity = Post::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("post_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Comment(
    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "post_id")
    val postId: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
