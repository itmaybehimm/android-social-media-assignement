package com.example.socialmedia.data.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.socialmedia.data.model.ReactionType
import com.example.socialmedia.data.model.User

import com.example.socialmedia.getCurrentDateTime
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(
        user: User
    ): Int

    @Delete
    suspend fun deleteUser(user:User)
    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE role = 'user'")
    fun getAllStudentsFlow(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByEmail(email: String): LiveData<User>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun getUserByEmailAndPasswordNonCurrent(email: String, password:String): LiveData<User>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<User?>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserByIdNonLive(userId: Int): User?

    @Query("UPDATE user SET updated_at = :updatedAt WHERE id = :userId")
    suspend fun updateTimestamp(userId: Int, updatedAt: String )

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @Transaction
    suspend fun insertAdminHardcoded() {
        val adminUser = User(
            fullName = "admin",
            email = "admin@gmail.com",
            dateOfBirth = "2003-04-10",
            password = "12345678",
            role = "admin",
            createdAt = getCurrentDateTime(),
            updatedAt = getCurrentDateTime()
        )
        insertUser(adminUser)
    }

}
