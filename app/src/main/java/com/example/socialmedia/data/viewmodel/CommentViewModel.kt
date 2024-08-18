package com.example.socialmedia.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.data.db.SocialMediaDatabase
import com.example.socialmedia.data.model.Comment
import com.example.socialmedia.data.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val commentDao = SocialMediaDatabase.getDatabaseInstance(application).commentDao()
    private val commentRepository = CommentRepository(commentDao)

    // This flow will be used for UI to observe changes
    private val _commentsByPostId = MutableStateFlow<Map<Int, List<Comment>>>(emptyMap())
    val commentsByPostId: StateFlow<Map<Int, List<Comment>>> = _commentsByPostId

    init {
        // Start observing comments changes and update the state flow accordingly
        viewModelScope.launch(Dispatchers.IO) {
            commentRepository.getAllComments()
                .collect { comments ->
                    // Convert comments to a map by postId
                    _commentsByPostId.value = comments.groupBy { it.postId }
                }
        }
    }

    fun getCommentsByPostId(postId: Int): StateFlow<List<Comment>> {
        return commentsByPostId
            .map { it[postId] ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun insertComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            commentRepository.insertComment(comment)
        }
    }

    fun updateComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            commentRepository.updateComment(comment)
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            commentRepository.deleteComment(comment)
        }
    }
}
