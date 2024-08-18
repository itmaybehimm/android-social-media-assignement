package com.example.socialmedia.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val scope = CoroutineScope(Dispatchers.IO) // Use IO dispatcher for DB operations
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    init {
        val userDao = SocialMediaDatabase.getDatabaseInstance(application).userDao()
        repository = UserRepository(userDao)
        loadUserSession()
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

    fun getUserByEmailAndPassword(email: String, password: String) {
        scope.launch {
            val user = repository.getUserByEmailAndPassword(email, password)
            _currentUser.postValue(user)
        }
    }

    fun getUserByEmailAndPasswordNonCurrent(email: String, password: String): LiveData<User> {
        return repository.getUserByEmailAndPasswordNonCurrent(email, password)
    }

    fun getUserById(userId: Int): LiveData<User?> {
        return repository.getUserById(userId)
    }

    fun saveUserSession(user: User) {
        val sharedPreferences = getApplication<Application>()
            .getSharedPreferences("user_prefs", Application.MODE_PRIVATE)
        sharedPreferences.edit().putInt("user_id", user.id).apply()
    }

    fun getUserSession(onResult: (User?) -> Unit) {
        scope.launch {
            val sharedPreferences = getApplication<Application>()
                .getSharedPreferences("user_prefs", Application.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("user_id", -1)
            val user = if (userId != -1) {
                repository.getUserByIdNonLive(userId)
            } else {
                null
            }
            onResult(user)
        }
    }

    private fun loadUserSession() {
        getUserSession { user ->
            _currentUser.postValue(user)
        }
    }

    fun clearUserSession() {
        val sharedPreferences = getApplication<Application>()
            .getSharedPreferences("user_prefs", Application.MODE_PRIVATE)
        sharedPreferences.edit().remove("user_id").apply()
        _currentUser.postValue(null)
    }

    fun logout() {
        scope.launch {
            clearUserSession()
        }
        _currentUser.postValue(null)
    }

}
