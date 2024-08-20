package com.example.socialmedia.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_post_reaction",
    indices = [Index(value = ["post_id", "user_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Post::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("post_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserPostReaction(
    @ColumnInfo(name = "post_id")
    val postId: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "reaction_type")
    val reactionType: ReactionType,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

enum class ReactionType {
    LIKE,
    DISLIKE
}
