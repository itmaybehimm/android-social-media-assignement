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

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<User>

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUserByUsernameAndPassword(username: String, password:String): LiveData<User>

}
