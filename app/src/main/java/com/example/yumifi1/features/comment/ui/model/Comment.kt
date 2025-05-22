package com.example.yumifi1.features.comment.ui.model

data class Comment(
    val id: Long? = null,
    val text: String = "",
    val isMy: Boolean = false,
)