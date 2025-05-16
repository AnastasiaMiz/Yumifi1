package com.example.yumifi1.features.reg.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.reg.ui.event.RegEvent
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
class RegViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val messageHandler: MessageHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(RegState())
    val state: StateFlow<RegState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<RegEvent>()
    val event: SharedFlow<RegEvent> = _event.asSharedFlow()

    fun onEmailChanged(email: String) {
        _state.update { state ->
            state.copy(
                email = email
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _state.update { state ->
            state.copy(
                password = password
            )
        }
    }

    fun onPasswordConfirmChanged(passwordConfirm: String) {
        _state.update { state ->
            state.copy(
                passwordConfirm = passwordConfirm
            )
        }
    }

    fun onRegisterClicked() {
        updateLoading(isLoading = true)
        val (email, password, confirmPassword) = _state.value
        val isValidPassword = validatePasswords(password, confirmPassword)
        viewModelScope.launch {
            if (isValidPassword) {
                authRepository.registration(
                    email = email,
                    password = password
                ).handleRegResult()
            } else {
                messageHandler.sendMessage(
                    message = "Пароли не совпадают"
                )
                updateLoading(isLoading = false)
            }
        }
    }

    private suspend fun Result<Unit>.handleRegResult() {
        this.onSuccess {
            _event.emit(RegEvent.OpenRecipesScreen)
        }
        .onFailure { error ->
            messageHandler.sendMessage(
                message = "Ошибка регистрации: ${error.message}"
            )
        }
        updateLoading(isLoading = false)
    }

    private fun validatePasswords(
        password: String,
        confirmPassword: String,
    ): Boolean = password.trim() == confirmPassword.trim()

    private fun updateLoading(isLoading: Boolean) {
        _state.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }
}