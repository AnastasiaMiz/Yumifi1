package com.example.yumifi1.features.comment.interactor

import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.comment.interactor.database.CommentDao
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity
import com.example.yumifi1.features.comment.ui.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val commentDao: CommentDao,
) {

    suspend fun insertComment(
        recipeId: Long,
        comment: Comment
    ): Result<Long> = withContext(Dispatchers.IO) {
        val userId = authRepository.getCurrentUserId()
        val commentEntity = CommentEntity(
            id = comment.id,
            text = comment.text,
            userOwnerId = userId,
            recipeOwnerId = recipeId,
        )
        val commentId = commentDao.insertComment(comment = commentEntity)
        Result.success(commentId)
    }

    suspend fun updateComment(
        comment: Comment
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val commentId = comment.id ?: return@withContext Result.failure(
            IllegalStateException("Невозможно обновить комментарий. Нужен ID")
        )
        commentDao.updateComment(
            commentId = commentId,
            text = comment.text,
        )
        Result.success(Unit)
    }

    suspend fun deleteComment(comment: Comment): Result<Unit> = withContext(Dispatchers.IO) {
        val commentId = comment.id ?: return@withContext Result.failure(
            IllegalStateException("Невозможно удалить комментарий. Нужен ID")
        )
        commentDao.deleteCommentById(commentId = commentId)
        Result.success(Unit)
    }

    suspend fun deleteCommentById(commentId: Long): Result<Unit> = withContext(Dispatchers.IO) {
        commentDao.deleteCommentById(commentId = commentId)
        Result.success(Unit)
    }

    suspend fun getComment(
        commentId: Long
    ): Result<Comment> = withContext(Dispatchers.IO) {
        val commentEntity = commentDao.getComment(commentId)
            ?: return@withContext Result.failure(
                IllegalStateException("Не удалось найти комментарий")
            )
        val comment = Comment(
            id = commentEntity.id,
            text = commentEntity.text,
        )
        Result.success(comment)
    }

    suspend fun isCommentOwnedUser(
        commentId: Long,
        userId: Long,
    ): Boolean = withContext(Dispatchers.IO) {
        commentDao.isCommentOwnedUser(
            commentId = commentId,
            userId = userId,
        )
    }
}