package com.example.socialmedia.data.repository

import com.example.socialmedia.data.db.PostDao
import com.example.socialmedia.data.model.Post
import kotlinx.coroutines.flow.Flow

class PostRepository(private val postDao: PostDao) {

    fun getAllPosts(): Flow<List<Post>> {
        return postDao.getAllPostsFlow()
    }

    suspend fun insertPost(post: Post): Long {
        return postDao.insertPost(post)
    }

    suspend fun updatePost(post: Post): Int {
        return postDao.updatePost(post)
    }

    suspend fun deletePost(post: Post) {
        postDao.deletePost(post)
    }

    fun getPostById(postId: Int): Flow<Post> {
        return postDao.getPostByIdFlow(postId)
    }

    suspend fun increaseLikeCount(postId: Int) {
        postDao.increaseLikeCount(postId)
    }

    suspend fun increaseDislikeCount(postId: Int) {
        postDao.increaseDislikeCount(postId)
    }
}
