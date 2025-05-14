package com.example.yumifi1.features.auth.interactor.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class UserConfig

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    private const val USER_CONFIG = "user_shared_preferences"

    @Provides
    @Singleton
    @UserConfig
    fun provideUserSharedPreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences = appContext.getSharedPreferences(
        USER_CONFIG,
        Context.MODE_PRIVATE,
    )
}