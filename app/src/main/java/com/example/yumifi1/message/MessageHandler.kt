package com.example.yumifi1.message

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageHandler @Inject constructor() {

    private val _event = MutableSharedFlow<MessageEvent>()
    val event: SharedFlow<MessageEvent> = _event.asSharedFlow()

    suspend fun sendMessage(message: String) {
        _event.emit(
            MessageEvent.SendMessage(message = message)
        )
    }
}