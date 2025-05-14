package com.example.yumifi1.features.auth.ui

/**
 * Состояние экрана авторизации
 * @property email Почта пользователя
 * @property password Пароль пользователя
 * @property isLoading Загрузка экрана
 */
data class AuthState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)