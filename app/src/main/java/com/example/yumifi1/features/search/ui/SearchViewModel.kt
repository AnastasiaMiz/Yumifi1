package com.example.yumifi1.features.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.product_details.interactor.ProductRepository
import com.example.yumifi1.features.recipe_details.interactor.RecipeRepository
import com.example.yumifi1.features.search.ui.data.SelectableData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val recipeRepository: RecipeRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        state.onEach { value ->
            val productsName = value.products
                .filter { it.isSelected }
                .map { it.product.name }
            val recipesFlow = recipeRepository.getRecipes(
                productsName = productsName,
            )
            searchJob?.cancel()
            searchJob = recipesFlow.onEach { recipes ->
                _state.update { state ->
                    state.copy(
                        recipes = recipes,
                    )
                }
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            productRepository.getProductsForUserFlow(
                userId = userId
            ).collect { products ->
                _state.update { state ->
                    state.copy(
                        products = products.map { product ->
                            SelectableData(product = product)
                        }
                    )
                }
            }
        }
    }

    fun onSelectProduct(item: SelectableData) {
        _state.update { state ->
            state.copy(
                products = state.products.map { selectableData ->
                    if (selectableData.product.id == item.product.id) {
                        selectableData.copy(
                            isSelected = !selectableData.isSelected,
                        )
                    } else {
                        selectableData
                    }
                }
            )
        }
    }
}