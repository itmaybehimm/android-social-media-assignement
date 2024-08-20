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

    @Query("SELECT * FROM post ORDER BY id DESC")
    fun getAllPostsFlow(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE id = :postId")
    fun getPostByIdFlow(postId: Int): Flow<Post>




    @Insert
    suspend fun insertPost(post: Post): Long

    @Update
    suspend fun updatePost(post: Post): Int

    @Delete
    suspend fun deletePost(post: Post)
}
