package com.example.socialmedia.data.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.socialmedia.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun deleteUser(user:User)

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByEmail(email: String): LiveData<User>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun getUserByEmailAndPasswordNonCurrent(email: String, password:String): LiveData<User>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<User?>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserByIdNonLive(userId: Int): User?

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?
}
