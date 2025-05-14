package com.example.yumifi1.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.auth.ui.event.AuthEvent
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
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<AuthEvent>()
    val event: SharedFlow<AuthEvent> = _event.asSharedFlow()

    /**
     * Сохраняет ввод пароля в состояние экрана
     * @param email Введенный email
     */
    fun onEmailChanged(email: String) {
        _state.update { state ->
            state.copy(email = email)
        }
    }

    /**
     * Сохраняет ввод пароля в состояние экрана
     * @param password Введенный пароль
     */
    fun onPasswordChanged(password: String) {
        _state.update { state ->
            state.copy(password = password)
        }
    }

    /**
     * Обработка нажатия на кнопку "Войти"
     */
    fun onLoginClicked() {
        val (email, password) = state.value
        updateLoading(isLoading = true)
        viewModelScope.launch {
            authRepository.login(
                email = email,
                password = password
            ).onSuccess {
                _event.emit(
                    AuthEvent.OpenRecipesScreen
                )
            }.onFailure { error ->
                _event.emit(
                    AuthEvent.ShowMessage(
                        message = "Ошибка авторизации: ${error.message}"
                    )
                )
            }
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}