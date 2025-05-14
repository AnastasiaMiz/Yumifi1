package com.example.yumifi1.features.auth.ui.event

/** События экрана авторизации */
sealed interface AuthEvent {
    /**
     * Показать сообщение
     * @property message Сообщение
     */
    data class ShowMessage(val message: String) : AuthEvent

    /**
     * Открыть экран списка рецептов
     */
    data object OpenRecipesScreen : AuthEvent
}