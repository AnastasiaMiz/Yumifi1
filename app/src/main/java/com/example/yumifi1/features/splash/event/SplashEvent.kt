package com.example.yumifi1.features.splash.event

/** События экрана сплеша */
sealed interface SplashEvent {

    /** Открыть экран авторизации */
    data object OpenAuthScreen : SplashEvent

    /** Открыть экран фильмов */
    data object OpenMoviesScreen : SplashEvent
}