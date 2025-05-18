package com.example.yumifi1.features.recipe_details.ui.handler

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddIngredientHandler @Inject constructor() {

    private val _addProductIdFlow = MutableSharedFlow<Long>()
    val addProductIdFlow: SharedFlow<Long> = _addProductIdFlow.asSharedFlow()

    suspend fun addProduct(productId: Long) {
        _addProductIdFlow.emit(productId)
    }
}