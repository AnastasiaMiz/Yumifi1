package com.example.yumifi1.features.recipes.ui.event

sealed interface RecipesEvent {

    data object OpenAuthView : RecipesEvent
}