package com.example.yumifi1.features.products.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.product_details.interactor.ProductRepository
import com.example.yumifi1.features.product_details.ui.model.Product
import com.example.yumifi1.features.products.ui.event.ProductsEvent
import com.example.yumifi1.features.recipe_details.ui.handler.AddIngredientHandler
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
class ProductsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val productRepository: ProductRepository,
    private val addIngredientHandler: AddIngredientHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state: StateFlow<ProductsState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProductsEvent>()
    val event: SharedFlow<ProductsEvent> = _event.asSharedFlow()

    private var isRoot: Boolean = true

    init {
        viewModelScope.launch {
            productRepository.getProductsForUserFlow(
                userId = authRepository.getCurrentUserId(),
            ).collect { products ->
                _state.update { state ->
                    state.copy(
                        products = products,
                    )
                }
            }
        }
        isRoot = savedStateHandle.get<Boolean>("isRoot") == true
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            authRepository.logout()
            _event.emit(ProductsEvent.OpenAuthView)
        }
    }

    fun onDeleteClicked(product: Product) {
        val productId = product.id ?: return
        viewModelScope.launch {
            productRepository.deleteProduct(productId)
        }
    }

    fun onProductClicked(productId: Long?) {
        if (productId == null) return
        viewModelScope.launch {
            addIngredientHandler.addProduct(productId)
        }
    }
}