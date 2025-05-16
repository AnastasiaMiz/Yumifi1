package com.example.yumifi1.features.auth.ui.event

/** События экрана авторизации */
sealed interface AuthEvent {
    /**
     * Открыть экран списка рецептов
     */
    data object OpenRecipesScreen : AuthEvent
}