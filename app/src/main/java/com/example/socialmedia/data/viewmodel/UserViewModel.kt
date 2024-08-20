package com.example.socialmedia.data.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.data.db.SocialMediaDatabase
import com.example.socialmedia.data.model.User
import com.example.socialmedia.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    private val scope = CoroutineScope(Dispatchers.IO) // Use IO dispatcher for DB operations
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser
    private val _allStudents = MutableStateFlow<List<User>>(emptyList())
    val allStudents: StateFlow<List<User>> get() = _allStudents
    init {
        val userDao = SocialMediaDatabase.getDatabaseInstance(application).userDao()
        repository = UserRepository(userDao)
        viewModelScope.launch {
            repository.getAllStudentsFlow()
                .collect { students ->
                    _allStudents.value = students
                }
        }

        Log.d("UserViewModel", "Initialized UserViewModel and loading user session...")
        loadUserSession()
    }

    fun insertAdminUser() {
        viewModelScope.launch {
            try {
                repository.insertAdminHardcoded()
            } catch (e: Exception) {
                // Admin exists

            }
        }
    }

    fun insertUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        scope.launch {
            try {
                // Perform validation before attempting to insert the user
                if (!isValidEmail(user.email)) {
                    withContext(Dispatchers.Main) {
                        onError("Invalid email format.")
                    }
                    return@launch
                }

                if (!isValidPassword(user.password)) {
                    withContext(Dispatchers.Main) {
                        onError("Password must be at least 8 characters long and contain letters and numbers.")
                    }
                    return@launch
                }

                if (!isValidDateOfBirth(user.dateOfBirth)) {
                    withContext(Dispatchers.Main) {
                        onError("Invalid date of birth format. Please use yyyy-MM-dd.")
                    }
                    return@launch
                }

                repository.insertUser(user)
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: SQLiteConstraintException) {
                withContext(Dispatchers.Main) {
                    onError("Email already exists.")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "An error occurred.")
                }
            }
        }
    }

    fun updateUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        scope.launch {
            try {
                if (!isValidEmail(user.email)) {
                    withContext(Dispatchers.Main) {
                        onError("Invalid email format.")
                    }
                    return@launch
                }
                if (!isValidPassword(user.password)) {
                    withContext(Dispatchers.Main) {
                        onError("Password must be at least 8 characters long.")
                    }
                    return@launch
                }

                if (!isValidDateOfBirth(user.dateOfBirth)) {
                    withContext(Dispatchers.Main) {
                        onError("Invalid date of birth format. Expected format: yyyy-MM-dd")
                    }
                    return@launch
                }

                repository.updateUser(user)

                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: SQLiteConstraintException) {
                withContext(Dispatchers.Main) {
                    onError("Email already exists.")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "An error occurred")
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun isValidDateOfBirth(dob: String): Boolean {
        // Assuming the format is "yyyy-MM-dd"
        val datePattern = Regex("""\d{4}-\d{2}-\d{2}""")
        return dob.matches(datePattern)
    }


    fun deleteUser(user: User) {

        scope.launch {
            try {
                repository.deleteUser(user)

            } catch (e: Exception) {
                Log.e("UserViewModel", "Error deleting user", e)
            }
        }
    }

    fun getUserByEmailAndPassword(email: String, password: String) {
        Log.d("UserViewModel", "Fetching user by email: $email")
        scope.launch {
            try {
                val user = repository.getUserByEmailAndPassword(email, password)
                Log.d("UserViewModel", "Fetched ${user?.email} with name ${user?.fullName} ")
                _currentUser.postValue(user)
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error fetching user by email and password", e)
            }
        }
    }

    fun getUserByEmailAndPasswordNonCurrent(email: String, password: String): LiveData<User> {

        return repository.getUserByEmailAndPasswordNonCurrent(email, password)
    }

    fun getUserById(userId: Int): LiveData<User?> {

        return repository.getUserById(userId)
    }

    fun saveUserSession(user: User) {
        Log.d("UserViewModel", "Saving user session for user ID: ${user.id}")
        val sharedPreferences = getApplication<Application>()
            .getSharedPreferences("user_prefs", Application.MODE_PRIVATE)
        sharedPreferences.edit().putInt("user_id", user.id).apply()
    }

    fun getUserSession(onResult: (User?) -> Unit) {
        Log.d("UserViewModel", "Fetching user session from SharedPreferences")
        scope.launch {
            try {
                val sharedPreferences = getApplication<Application>()
                    .getSharedPreferences("user_prefs", Application.MODE_PRIVATE)
                val userId = sharedPreferences.getInt("user_id", -1)
                Log.d("UserViewModel", "User session found with user ID: $userId")
                val user = if (userId != -1) {
                    repository.getUserByIdNonLive(userId)
                } else {
                    null
                }
                Log.d("UserViewModel", "User session user: ${user?.email}")
                onResult(user)
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error fetching user session", e)
            }
        }
    }

    private suspend fun clearUserSession() {
        Log.d("UserViewModel", "Saving user session for user ID: ${-1}")
        val sharedPreferences = getApplication<Application>()
            .getSharedPreferences("user_prefs", Application.MODE_PRIVATE)
        sharedPreferences.edit().putInt("user_id", -1).apply()
    }

    fun logout(onComplete: () -> Unit) {
        Log.d("UserViewModel", "Logging out user...")
        scope.launch {
            try {
                clearUserSession()
                withContext(Dispatchers.Main) {
                    _currentUser.value = null  // Use setValue on the main thread
                }
                Log.d("UserViewModel", "Logout successful, _currentUser is now ${_currentUser.value}")
                onComplete()
            } catch (e: Exception) {
                Log.e("UserViewModel", "Logout error", e)
            }
        }
    }

    private fun loadUserSession() {
        Log.d("UserViewModel", "Loading user session...")
        scope.launch {
            getUserSession { user ->
                Log.d("UserViewModel", "Setting _currentUser with loaded session user: ${user?.email}")
                _currentUser.postValue(user)
            }
        }
    }
}
