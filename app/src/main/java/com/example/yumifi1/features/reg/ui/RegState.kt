package com.example.yumifi1.features.reg.ui

data class RegState(
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val isLoading: Boolean = false,
)