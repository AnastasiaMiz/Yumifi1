package com.example.yumifi1.features.comment.interactor.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yumifi1.features.comment.interactor.database.entity.COMMENTS_TABLE
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity.Companion.COMMENT_ID
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity.Companion.COMMENT_TEXT
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity.Companion.COMMENT_USER_OWNER
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity): Long

    @Update
    suspend fun updateComment(comment: CommentEntity)

    @Query("UPDATE $COMMENTS_TABLE SET $COMMENT_TEXT = :text WHERE $COMMENT_ID = :commentId")
    suspend fun updateComment(
        commentId: Long,
        text: String,
    )

    @Delete
    suspend fun deleteComment(comment: CommentEntity)

    @Query("DELETE FROM $COMMENTS_TABLE WHERE $COMMENT_ID = :commentId")
    suspend fun deleteCommentById(commentId: Long)

    @Query("SELECT * FROM $COMMENTS_TABLE WHERE $COMMENT_USER_OWNER = :userId")
    fun getCommentsForUser(userId: Int): Flow<List<CommentEntity>>
}