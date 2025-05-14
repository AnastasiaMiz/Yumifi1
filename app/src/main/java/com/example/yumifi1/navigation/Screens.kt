package com.example.yumifi1.navigation

/**
 * Маршруты экрана
 */
sealed class Screens(val route: String) {
    /**
     * Сплеш экран
     */
    data object Splash : Screens("splash")

    /**
     * Экран авторизации
     */
    data object Auth : Screens("auth")

    /**
     * Экран регистрации
     */
    data object Reg : Screens("reg")

    /**
     * Экран рецептов
     */
    data object Recipes : Screens("Recipes")
}