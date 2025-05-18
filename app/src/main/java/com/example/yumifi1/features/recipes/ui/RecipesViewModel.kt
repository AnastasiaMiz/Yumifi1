package com.example.yumifi1.features.recipes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.recipe_details.interactor.RecipeRepository
import com.example.yumifi1.features.recipes.ui.event.RecipesEvent
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
class RecipesViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RecipesState())
    val state: StateFlow<RecipesState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<RecipesEvent>()
    val event: SharedFlow<RecipesEvent> = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            recipeRepository.getRecipes(userId)
                .collect { recipes ->
                    _state.update { state ->
                        state.copy(
                            recipes = recipes,
                        )
                    }
                }
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            authRepository.logout()
            _event.emit(RecipesEvent.OpenAuthView)
        }
    }
}