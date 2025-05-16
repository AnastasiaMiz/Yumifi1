package com.example.yumifi1.event

sealed interface MainEvent {

    data class ShowMessage(
        val message: String,
    ) : MainEvent
}