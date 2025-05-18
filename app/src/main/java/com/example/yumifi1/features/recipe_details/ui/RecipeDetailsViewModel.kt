package com.example.yumifi1.features.recipe_details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.product_details.interactor.ProductRepository
import com.example.yumifi1.features.recipe_details.ui.data.RecipeDetailsTab
import com.example.yumifi1.features.recipe_details.ui.event.RecipeDetailsEvent
import com.example.yumifi1.features.recipe_details.ui.handler.AddIngredientHandler
import com.example.yumifi1.features.recipes.ui.model.Recipe.Ingredient
import com.example.yumifi1.message.MessageHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    addIngredientHandler: AddIngredientHandler,
    private val productRepository: ProductRepository,
    private val messageHandler: MessageHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailsState())
    val state: StateFlow<RecipeDetailsState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<RecipeDetailsEvent>()
    val event: SharedFlow<RecipeDetailsEvent> = _event.asSharedFlow()

    init {
        addIngredientHandler.addProductIdFlow
            .onEach { productId ->
                addIngredient(productId)
            }
            .launchIn(viewModelScope)
    }

    fun onNameChanged(name: String) {
        _state.update { state ->
            state.copy(
                recipe = state.recipe.copy(
                    name = name,
                )
            )
        }
    }

    fun selectTab(tab: RecipeDetailsTab) {
        _state.update { state ->
            state.copy(
                selectedTab = tab,
            )
        }
    }

    fun onSaveClicked() {
        // TODO: сохранять рецепт
    }

    fun onDescriptionChanged(description: String) {
        _state.update { state ->
            state.copy(
                recipe = state.recipe.copy(
                    description = description,
                )
            )
        }
    }

    fun onDeleteIngredientClicked(ingredient: Ingredient) {
        _state.update { state ->
            val ingredients = state.recipe.ingredients.filter { existedIngredient ->
                existedIngredient.product.id != ingredient.product.id
            }
            state.copy(
                recipe = state.recipe.copy(
                    ingredients = ingredients,
                )
            )
        }
    }

    private suspend fun addIngredient(productId: Long) {
        val currentIngredients = state.value.recipe.ingredients
        val alreadyHasIngredient = currentIngredients.any { ingredient ->
            ingredient.product.id == productId
        }
        if (alreadyHasIngredient) return

        productRepository.getProduct(productId)
            .onSuccess { product ->
                val newIngredient = Ingredient(
                    product = product,
                    quantity = 1,
                )
                _state.update { state ->
                    state.copy(
                        recipe = state.recipe.copy(
                            ingredients = buildList {
                                addAll(state.recipe.ingredients)
                                add(newIngredient)
                            },
                        )
                    )
                }
            }
            .onFailure { error ->
                messageHandler.sendMessage("Ошибка: ${error.message}")
            }
    }
}