package com.example.yumifi1.features.recipes.ui.model

data class Recipe(
    val name: String,
    val ingredients: List<Ingredient>
) {
    data class Ingredient(
        val product: Product,
        val quantity: Int,
    )
}