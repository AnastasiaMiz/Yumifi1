package com.example.yumifi1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.event.MainEvent
import com.example.yumifi1.message.MessageEvent
import com.example.yumifi1.message.MessageHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    messageHandler: MessageHandler,
) : ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event: SharedFlow<MainEvent> = _event.asSharedFlow()

    init {
        messageHandler.event
            .onEach(::handleMessageEvent)
            .launchIn(viewModelScope)
    }

    private suspend fun handleMessageEvent(messageEvent: MessageEvent) {
        when (messageEvent) {
            is MessageEvent.SendMessage -> {
                _event.emit(
                    MainEvent.ShowMessage(message = messageEvent.message)
                )
            }
        }
    }
}