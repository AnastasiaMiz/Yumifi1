package com.example.yumifi1.features.recipe_details.ui.model

import com.example.yumifi1.features.product_details.ui.model.Product

data class Recipe(
    val id: Long? = null,
    val name: String = "",
    val description: String = "",
    val ingredients: List<Ingredient> = emptyList()
) {
    data class Ingredient(
        val id: Long? = null,
        val product: Product,
        val quantity: Int,
    )
}