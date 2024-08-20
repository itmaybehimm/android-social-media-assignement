package com.example.socialmedia.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialmedia.data.model.ReactionType
import com.example.socialmedia.data.model.UserPostReaction
import kotlinx.coroutines.flow.Flow

@Dao
interface UserReactionDao {

    @Insert
    suspend fun insertReaction(reaction: UserPostReaction)

    @Query("SELECT * FROM user_post_reaction WHERE user_id = :userId AND post_id = :postId")
    suspend fun getReaction(userId: Int, postId: Int): UserPostReaction?

    @Query("DELETE FROM user_post_reaction WHERE user_id = :userId AND post_id = :postId")
    suspend fun deleteReaction(userId: Int, postId: Int)

    @Query("DELETE FROM user_post_reaction WHERE user_id = :userId")
    suspend fun deleteReactionsForUser(userId: Int)

    @Query("DELETE FROM user_post_reaction WHERE post_id = :postId")
    suspend fun deleteReactionsForPost(postId: Int)

    @Query("SELECT reaction_type FROM user_post_reaction WHERE user_id = :userId AND post_id = :postId LIMIT 1")
    fun getCurrentReaction(userId: Int, postId: Int): Flow<ReactionType?>

    @Query("SELECT COUNT(*) FROM user_post_reaction WHERE post_id = :postId AND reaction_type = :reactionType")
    fun getReactionCount(postId: Int, reactionType: ReactionType): Flow<Int>
}
