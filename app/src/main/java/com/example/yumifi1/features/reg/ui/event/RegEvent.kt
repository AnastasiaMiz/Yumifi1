package com.example.yumifi1.features.reg.ui.event

import androidx.annotation.StringRes

sealed interface RegEvent {
    /**
     * Показать сообщение
     * @property message Сообщение
     */
    data class ShowMessageRes(@StringRes val messageRes: Int) : RegEvent

    data class ShowMessage(val message: String) : RegEvent

    /**
     * Открыть экран списка рецептов
     */
    data object OpenRecipesScreen : RegEvent
}