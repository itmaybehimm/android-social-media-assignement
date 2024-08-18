package com.example.socialmedia.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.socialmedia.data.model.*

@Database(
    entities = [User::class],
    version =1
)
abstract class SocialMediaDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao


    companion object {
        @Volatile
        private var INSTANCE: SocialMediaDatabase? = null

        fun getDatabaseInstance(context: Context): SocialMediaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SocialMediaDatabase::class.java,
                    "note-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}

