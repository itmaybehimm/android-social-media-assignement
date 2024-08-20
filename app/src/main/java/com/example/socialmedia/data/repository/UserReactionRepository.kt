package com.example.socialmedia.data.repository




import com.example.socialmedia.data.db.UserReactionDao
import com.example.socialmedia.data.model.UserPostReaction
import com.example.socialmedia.data.model.ReactionType
import kotlinx.coroutines.flow.Flow

class UserPostReactionRepository(private val dao: UserReactionDao) {

    suspend fun addReaction(userId: Int, postId: Int, reactionType: ReactionType) {
        val existingReaction = dao.getReaction(userId, postId)
        if (existingReaction != null) {
            if (existingReaction.reactionType != reactionType) {
                // Update the existing reaction
                dao.deleteReaction(userId, postId)
                dao.insertReaction(existingReaction.copy(reactionType = reactionType))
            }
        } else {
            // Insert new reaction
            dao.insertReaction(UserPostReaction(userId = userId, postId = postId, reactionType = reactionType))
        }
    }

    suspend fun removeReaction(userId: Int, postId: Int) {
        dao.deleteReaction(userId, postId)
    }

    suspend fun getUserReaction(userId: Int, postId: Int): ReactionType? {
        val reaction = dao.getReaction(userId, postId)
        return reaction?.reactionType
    }

    fun getReactionCount(postId: Int, reactionType: ReactionType): Flow<Int> {
        return dao.getReactionCount(postId, reactionType)
    }


    fun getCurrentReaction(userId: Int, postId: Int): Flow<ReactionType?> {
        return dao.getCurrentReaction(userId, postId)
    }
}
