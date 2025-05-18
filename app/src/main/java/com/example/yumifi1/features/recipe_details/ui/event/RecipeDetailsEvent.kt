package com.example.yumifi1.features.recipe_details.ui.event

sealed interface RecipeDetailsEvent {

    data object OnBackNavigate : RecipeDetailsEvent
}