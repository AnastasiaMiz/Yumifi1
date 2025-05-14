package com.example.yumifi1.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.splash.event.SplashEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<SplashEvent>()
    val event: SharedFlow<SplashEvent> = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val timer = viewModelScope.launch { startSplashTimer() }
            val isAuthorized = authRepository.isAuthorized()
            timer.join()

            val openNextScreenEvent = if (isAuthorized) {
                SplashEvent.OpenMoviesScreen
            } else {
                SplashEvent.OpenAuthScreen
            }
            _event.emit(openNextScreenEvent)
        }
    }

    private suspend fun startSplashTimer(
        time: Long = 3 * 1000L
    ) {
        delay(time)
    }
}