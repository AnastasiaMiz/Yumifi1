package com.example.yumifi1.features.profile.ui.event

sealed interface ProfileEvent {

    data object OpenAuthView : ProfileEvent
}