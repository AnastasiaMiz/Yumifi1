package com.example.yumifi1.features.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.profile.ui.data.ProfileTab
import com.example.yumifi1.features.profile.ui.event.ProfileEvent
import com.example.yumifi1.features.profile.ui.model.UserWithContent
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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event: SharedFlow<ProfileEvent> = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            authRepository.getUserWithContent(userId)
                .collect(::handleGetData)
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            authRepository.logout()
            _event.emit(ProfileEvent.OpenAuthView)
        }
    }

    fun onEmailChanged(email: String) {
        _state.update { state ->
            state.copy(
                user = state.user.copy(
                    email = email,
                )
            )
        }
    }

    fun selectTab(tab: ProfileTab) {
        _state.update { state ->
            state.copy(
                selectedTab = tab,
            )
        }
    }

    private fun handleGetData(data: UserWithContent) {
        _state.update { state ->
            state.copy(
                user = data.user,
                recipes = data.recipes,
                comments = data.comments,
            )
        }
    }
}