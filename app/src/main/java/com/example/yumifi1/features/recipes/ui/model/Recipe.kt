package com.example.yumifi1.features.recipes.ui.model

import com.example.yumifi1.features.product_details.ui.model.Product

data class Recipe(
    val name: String,
    val ingredients: List<Ingredient>
) {
    data class Ingredient(
        val product: Product,
        val quantity: Int,
    )
}