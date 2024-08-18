package com.example.socialmedia.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.example.socialmedia.data.db.SocialMediaDatabase
import com.example.socialmedia.data.model.User
import com.example.socialmedia.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    protected val scope = CoroutineScope(Dispatchers.Main)
    init {
        val userDao = SocialMediaDatabase.getDatabaseInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun insertUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        scope.launch {
            try {
                repository.insertUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error occurred")
            }
        }
    }
    fun updateUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        scope.launch {
            try {
                repository.updateUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error occurred")
            }
        }
    }

    fun deleteUser(user: User) {
        scope.launch {
            repository.deleteUser(user)
        }
    }

    fun getUserByEmailAndPassword(email: String,password:String): LiveData<User> {
        return repository.getUserByEmailAndPassword(email,password)
    }
    fun getUserById(userId: Int): LiveData<User?> {
        return repository.getUserById(userId)
    }

}
