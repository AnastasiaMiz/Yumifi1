package com.example.yumifi1.features.recipe_details.ui

import androidx.lifecycle.ViewModel
import com.example.yumifi1.features.recipe_details.ui.event.RecipeDetailsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailsState())
    val state: StateFlow<RecipeDetailsState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<RecipeDetailsEvent>()
    val event: SharedFlow<RecipeDetailsEvent> = _event.asSharedFlow()

    fun onNameChanged(name: String) {
        _state.update { state ->
            state.copy(
                recipe = state.recipe.copy(
                    name = name,
                )
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

    fun onAddIngredientClicked() {

    }
}