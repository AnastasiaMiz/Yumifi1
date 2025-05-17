package com.example.yumifi1.features.product_details.ui.model

/**
 * Модель продукта
 * @property id продукта
 * @property name Название продукта
 */
data class Product(
    val id: Int? = null,
    val name: String,
    val unit: ProductUnit,
)