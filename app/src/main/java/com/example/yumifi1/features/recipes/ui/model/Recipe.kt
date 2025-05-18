package com.example.yumifi1.features.recipes.ui.model

import com.example.yumifi1.features.product_details.ui.model.Product

data class Recipe(
    val id: Int? = null,
    val name: String = "",
    val description: String = "",
    val ingredients: List<Ingredient> = emptyList()
) {
    data class Ingredient(
        val product: Product,
        val quantity: Int,
    )
}