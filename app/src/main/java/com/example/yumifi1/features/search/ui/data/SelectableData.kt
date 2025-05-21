package com.example.yumifi1.features.search.ui.data

import com.example.yumifi1.features.product_details.ui.model.Product

data class SelectableData(
    val product: Product,
    val isSelected: Boolean = false,
)