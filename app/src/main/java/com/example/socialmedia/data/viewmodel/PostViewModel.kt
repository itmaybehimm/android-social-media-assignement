package com.example.socialmedia.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.data.db.SocialMediaDatabase
import com.example.socialmedia.data.model.Post
import com.example.socialmedia.data.model.ReactionType
import com.example.socialmedia.data.repository.PostRepository
import com.example.socialmedia.data.repository.UserPostReactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val postDao = SocialMediaDatabase.getDatabaseInstance(application).postDao()
    private val userPostReactionDao = SocialMediaDatabase.getDatabaseInstance(application).userReactionDao()

    private val postRepository = PostRepository(postDao)
    private val userPostReactionRepository = UserPostReactionRepository(userPostReactionDao)

    private val _allPosts = MutableStateFlow<List<Post>>(emptyList())
    val allPosts: StateFlow<List<Post>> = _allPosts

    init {
        viewModelScope.launch {
            postRepository.getAllPosts().collect { posts ->
                _allPosts.value = posts
            }
        }
    }

    fun addPost(post: Post) {
        viewModelScope.launch {
            postRepository.insertPost(post) // Ensure this method is implemented in your repository
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            postRepository.deletePost(post) // Ensure this method is implemented in your repository
        }
    }

    fun getPostById(postId: Int): Flow<Post?> {
        return postRepository.getPostById(postId)
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            postRepository.updatePost(post) // Ensure this method is implemented in your repository
        }
    }

    fun addLike(userId: Int, postId: Int) {
        viewModelScope.launch {
            // Check the current reaction
            val currentReaction = userPostReactionRepository.getUserReaction(userId, postId)
            when (currentReaction) {
                ReactionType.DISLIKE -> {
                    // Remove dislike if it exists
                    userPostReactionRepository.removeReaction(userId, postId)
                }
                else -> {
                    // No reaction or different reaction
                }
            }
            // Add like
            userPostReactionRepository.addReaction(userId, postId, ReactionType.LIKE)
        }
    }

    fun addDislike(userId: Int, postId: Int) {
        viewModelScope.launch {
            // Check the current reaction
            val currentReaction = userPostReactionRepository.getUserReaction(userId, postId)
            when (currentReaction) {
                ReactionType.LIKE -> {
                    // Remove like if it exists
                    userPostReactionRepository.removeReaction(userId, postId)
                }
                else -> {
                    // No reaction or different reaction
                }
            }
            // Add dislike
            userPostReactionRepository.addReaction(userId, postId, ReactionType.DISLIKE)
        }
    }

    suspend fun getUserReaction(userId: Int, postId: Int): ReactionType? {
        return userPostReactionRepository.getUserReaction(userId, postId)
    }

    fun getLikeCount(postId: Int): Flow<Int> {
        return userPostReactionRepository.getReactionCount(postId, ReactionType.LIKE)
    }

    fun getDislikeCount(postId: Int): Flow<Int> {
        return userPostReactionRepository.getReactionCount(postId, ReactionType.DISLIKE)
    }

    fun getCurrentReaction(userId: Int, postId: Int): Flow<ReactionType?> {
        return userPostReactionRepository.getCurrentReaction(userId, postId)
    }
}