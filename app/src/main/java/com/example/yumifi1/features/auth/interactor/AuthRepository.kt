package com.example.yumifi1.features.auth.interactor

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.yumifi1.features.auth.interactor.database.UserDao
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.auth.interactor.di.UserConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.Base64
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDao,
    @UserConfig private val userSharedPreferences: SharedPreferences
) {
    private val messageDigest = MessageDigest.getInstance("SHA-256")

    suspend fun getCurrentUserId(): Int = withContext(Dispatchers.IO) {
        userSharedPreferences.getInt(USER_ID_KEY, UNDEFINED_USER_ID)
    }

    suspend fun isAuthorized(): Boolean = withContext(Dispatchers.IO) {
        val userId = getCurrentUserId()
        return@withContext userId != UNDEFINED_USER_ID
    }

    suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val existedUser = userDao.getByEmail(email)
            ?: return@withContext Result.failure(
                IllegalStateException("Пользователя не существует")
            )
        val hashedPassword = hashPassword(password)
        if (existedUser.password != hashedPassword) {
            return@withContext Result.failure(
                IllegalStateException("Некорректный пароль")
            )
        }
        if (existedUser.id == null) {
            return@withContext Result.failure(
                IllegalStateException("Не удалось получить пользователя")
            )
        }
        userSharedPreferences.edit(commit = true) {
            putInt(USER_ID_KEY, existedUser.id)
        }
        return@withContext Result.success(Unit)
    }

    suspend fun logout() = withContext(Dispatchers.IO) {
        userSharedPreferences.edit(commit = true) {
            putInt(USER_ID_KEY, UNDEFINED_USER_ID)
        }
    }

    suspend fun registration(
        email: String,
        password: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val existedUser = userDao.getByEmail(email)
        if (existedUser != null) {
            return@withContext Result.failure(
                IllegalStateException("Пользователь уже зарегистрирован")
            )
        }
        val hashedPassword = hashPassword(password)
        val newUser = UserEntity(
            email = email,
            password = hashedPassword
        )
        userDao.insertUser(newUser)

        login(
            email = email,
            password = password,
        )
    }

    private fun hashPassword(password: String): String {
        val hashedPassword = messageDigest.digest(password.toByteArray())
        return Base64.getEncoder().encodeToString(hashedPassword)
    }

    private companion object {
        const val USER_ID_KEY = "USER_ID_KEY"
        const val UNDEFINED_USER_ID = -1
    }
}