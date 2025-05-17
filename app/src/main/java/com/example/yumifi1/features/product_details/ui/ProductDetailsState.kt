package com.example.yumifi1.features.product_details.ui

import com.example.yumifi1.features.product_details.ui.model.Product
import com.example.yumifi1.features.product_details.ui.model.ProductUnit

data class ProductDetailsState(
    val product: Product = Product(
        name = "",
        unit = ProductUnit.PIECES,
    ),
    val isLoading: Boolean = false,
)