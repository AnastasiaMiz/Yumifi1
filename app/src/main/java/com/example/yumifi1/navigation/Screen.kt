package com.example.yumifi1.navigation

import kotlinx.serialization.Serializable

/**
 * Маршруты экрана
 */
sealed class Screen(val route: String) {
    /**
     * Сплеш экран
     */
    data object Splash : Screen("splash")

    /**
     * Экран авторизации
     */
    data object Auth : Screen("auth")

    /**
     * Экран регистрации
     */
    data object Reg : Screen("reg")

    /**
     * Экран с табами
     */
    data object Home : Screen("home")
}

sealed class TabScreen(route: String) : Screen(route) {

    /**
     * Экран рецептов
     */
    data object Recipes : TabScreen("recipes")

    companion object {
        private val tabRoutes = listOf(
            Recipes.route,
        )

        fun String.isTabRoute(): Boolean = tabRoutes.contains(this)
    }
}