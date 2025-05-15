package com.example.yumifi1.features.products.ui.event

sealed interface ProductsEvent {

    data object OpenAuthView : ProductsEvent
}