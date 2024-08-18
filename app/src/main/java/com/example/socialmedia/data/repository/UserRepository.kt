package com.example.socialmedia.data.repository

import androidx.lifecycle.LiveData
import com.example.socialmedia.data.db.UserDao
import com.example.socialmedia.data.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun updateUser(user: User): Int {
        return userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    fun getUserByEmail(email: String): LiveData<User> {
        return userDao.getUserByEmail(email)
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    fun getUserByEmailAndPasswordNonCurrent(email: String,password:String): LiveData<User> {
        return userDao.getUserByEmailAndPasswordNonCurrent(email,password)
    }

    fun getUserById(userId: Int): LiveData<User?> {
        return userDao.getUserById(userId)
    }

    fun getUserByIdNonLive(userId: Int): User? {
        return userDao.getUserByIdNonLive(userId)
    }
}
