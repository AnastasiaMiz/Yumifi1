package com.example.yumifi1.features.comment.ui.event

sealed interface CommentEvent {

    data object OnBackNavigate : CommentEvent
}