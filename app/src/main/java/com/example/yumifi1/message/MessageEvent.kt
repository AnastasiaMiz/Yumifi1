package com.example.yumifi1.message

sealed interface MessageEvent {
    data class SendMessage(
        val message: String,
    ) : MessageEvent
}