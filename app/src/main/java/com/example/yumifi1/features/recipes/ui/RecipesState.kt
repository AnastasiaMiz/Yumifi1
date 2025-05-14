package com.example.yumifi1.features.recipes.ui

import com.example.yumifi1.features.recipes.ui.model.Recipe

data class RecipesState(
    val recipes: List<Recipe> = emptyList()
)