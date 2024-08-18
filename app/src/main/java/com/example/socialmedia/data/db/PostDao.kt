package com.example.socialmedia.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.socialmedia.data.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun getAllPostsFlow(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE id = :postId")
    fun getPostByIdFlow(postId: Int): Flow<Post>

    @Query("UPDATE post SET like_count = like_count + 1 WHERE id = :postId")
    suspend fun increaseLikeCount(postId: Int)

    @Query("UPDATE post SET dislike_count = dislike_count + 1 WHERE id = :postId")
    suspend fun increaseDislikeCount(postId: Int)

    @Insert
    suspend fun insertPost(post: Post): Long

    @Update
    suspend fun updatePost(post: Post): Int

    @Delete
    suspend fun deletePost(post: Post)
}
