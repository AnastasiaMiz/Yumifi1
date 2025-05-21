package com.example.yumifi1.features.search.ui

import com.example.yumifi1.features.recipe_details.ui.model.Recipe
import com.example.yumifi1.features.search.ui.data.SelectableData

data class SearchState(
    val products: List<SelectableData> = emptyList(),
    val recipes: List<Recipe> = emptyList()
)