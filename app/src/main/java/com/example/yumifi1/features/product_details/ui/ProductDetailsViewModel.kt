package com.example.yumifi1.features.product_details.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.product_details.interactor.ProductRepository
import com.example.yumifi1.features.product_details.ui.event.ProductDetailsEvent
import com.example.yumifi1.features.product_details.ui.model.ProductUnit
import com.example.yumifi1.message.MessageHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val messageHandler: MessageHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailsState())
    val state: StateFlow<ProductDetailsState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProductDetailsEvent>()
    val event: SharedFlow<ProductDetailsEvent> = _event.asSharedFlow()

    init {
        val productId = savedStateHandle.get<Long>("productId")?.takeIf { it != -1L }
        if (productId != null) {
            viewModelScope.launch {
                updateLoading(isLoading = true)
                productRepository.getProduct(productId = productId)
                    .onSuccess { product ->
                        _state.update { state ->
                            state.copy(product = product)
                        }
                    }
                    .onFailure { error ->
                        messageHandler.sendMessage(
                            message = "Произошла ошибка: ${error.message}"
                        )
                    }
                updateLoading(isLoading = false)
            }
        }
    }

    fun onNameChanged(name: String) {
        _state.update { state ->
            state.copy(
                product = state.product.copy(
                    name = name,
                )
            )
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            val currentState = state.value
            productRepository.saveProduct(
                product = currentState.product
            ).onSuccess {
                _event.emit(ProductDetailsEvent.OnBackNavigate)
            }.onFailure { error ->
                messageHandler.sendMessage(
                    message = "Произошла ошибка: ${error.message}"
                )
            }
        }
    }

    fun onUnitClicked(unit: ProductUnit) {
        _state.update { state ->
            state.copy(
                product = state.product.copy(
                    unit = unit,
                )
            )
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        _state.update { state ->
            state.copy(isLoading = isLoading)
        }
    }
}