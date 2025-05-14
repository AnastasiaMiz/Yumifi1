package com.example.yumifi1.features.auth.interactor

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {

    suspend fun isAuthorized(): Boolean {
        return false
    }

    suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> {
        return Result.success(Unit)
    }

    suspend fun registration(
        email: String,
        password: String,
    ): Result<Unit> {
        return Result.success(Unit)
    }
}