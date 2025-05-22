package com.example.yumifi1.features.comment.ui

import com.example.yumifi1.features.comment.ui.model.Comment

data class CommentState(
    val recipeId: Long? = null,
    val comment: Comment = Comment(),
    val isCommentOwnedUser: Boolean = false,
    val isLoading: Boolean = false,
)