package com.example.socialmedia.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.data.db.SocialMediaDatabase
import com.example.socialmedia.data.model.Post
import com.example.socialmedia.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val postDao = SocialMediaDatabase.getDatabaseInstance(application).postDao()
    private val postRepository = PostRepository(postDao)

    private val _allPosts = MutableStateFlow<List<Post>>(emptyList())
    val allPosts: StateFlow<List<Post>> = _allPosts

    init {
        viewModelScope.launch {
            postRepository.getAllPosts().collect { posts ->
                _allPosts.value = posts
            }
        }
    }

    fun increaseLikeCount(postId: Int) {
        viewModelScope.launch {
            postRepository.increaseLikeCount(postId)
        }
    }

    fun increaseDislikeCount(postId: Int) {
        viewModelScope.launch {
            postRepository.increaseDislikeCount(postId)
        }
    }

    fun addPost(post: Post) {
        viewModelScope.launch {
            postRepository.insertPost(post) // Ensure this method is implemented in your repository
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
}
