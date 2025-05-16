package com.example.yumifi1.features.reg.ui.event

sealed interface RegEvent {
    /**
     * Открыть экран списка рецептов
     */
    data object OpenRecipesScreen : RegEvent
}