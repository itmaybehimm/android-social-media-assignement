package com.example.socialmedia.data.repository

import com.example.socialmedia.data.dao.CommentDao
import com.example.socialmedia.data.model.Comment
import kotlinx.coroutines.flow.Flow

class CommentRepository(private val commentDao: CommentDao) {

    fun getAllComments(): Flow<List<Comment>> {
        return commentDao.getAllComments()
    }

    fun getCommentsByPostId(postId: Int): Flow<List<Comment>> {
        return commentDao.getCommentsByPostId(postId)
    }

    suspend fun insertComment(comment: Comment) {
        commentDao.insertComment(comment)
    }

    suspend fun updateComment(comment: Comment) {
        commentDao.updateComment(comment)
    }

    suspend fun deleteComment(comment: Comment) {
        commentDao.deleteComment(comment)
    }
}
