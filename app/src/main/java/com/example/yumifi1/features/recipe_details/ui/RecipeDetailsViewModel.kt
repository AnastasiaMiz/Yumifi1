package com.example.yumifi1.features.recipe_details.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailsState())
    val state: StateFlow<RecipeDetailsState> = _state.asStateFlow()
}