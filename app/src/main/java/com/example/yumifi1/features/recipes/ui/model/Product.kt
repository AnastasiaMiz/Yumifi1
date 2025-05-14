package com.example.yumifi1.features.recipes.ui.model

/**
 * Модель продукта
 * @property id продукта
 * @property name Название продукта
 * @property unit Единица измерения
 */
data class Product(
    val id: Int,
    val name: String,
    val unit: String,
)