package com.example.yumifi1.features.recipes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.recipe_details.interactor.RecipeRepository
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RecipesState())
    val state: StateFlow<RecipesState> = _state.asStateFlow()

    init {
        recipeRepository.getAllRecipes()
            .onEach { recipes ->
                _state.update { state ->
                    state.copy(
                        recipes = recipes,
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}