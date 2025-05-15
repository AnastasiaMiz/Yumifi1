package com.example.yumifi1.features.products.ui

import com.example.yumifi1.features.products.ui.model.Product

data class ProductsState(
    val products: List<Product> = emptyList(),
)