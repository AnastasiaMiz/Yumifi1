package com.example.yumifi1.features.product_details.ui.event

sealed interface ProductDetailsEvent {

    data object OnBackNavigate : ProductDetailsEvent
}