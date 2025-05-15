package com.example.yumifi1.navigation

import kotlinx.serialization.Serializable

/**
 * Маршруты экрана
 */
sealed interface Screen {
    /**
     * Сплеш экран
     */
    @Serializable
    data object Splash : Screen

    /**
     * Экран авторизации
     */
    @Serializable
    data object Auth : Screen

    /**
     * Экран регистрации
     */
    @Serializable
    data object Reg : Screen

    /**
     * Экран с табами
     */
    @Serializable
    data object Home : Screen
}

sealed interface TabScreen : Screen {

    /**
     * Экран рецептов
     */
    @Serializable
    data object Recipes : TabScreen
}