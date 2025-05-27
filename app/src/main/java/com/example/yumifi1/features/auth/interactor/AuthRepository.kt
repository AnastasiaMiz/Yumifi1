package com.example.yumifi1.features.auth.interactor

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.yumifi1.features.auth.interactor.database.UserDao
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.auth.interactor.database.entity.mapToDomain
import com.example.yumifi1.features.auth.interactor.di.UserConfig
import com.example.yumifi1.features.comment.interactor.database.entity.mapToDomain
import com.example.yumifi1.features.product_details.interactor.database.ProductDao
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity
import com.example.yumifi1.features.product_details.ui.model.Product
import com.example.yumifi1.features.product_details.ui.model.ProductUnit
import com.example.yumifi1.features.profile.ui.model.UserWithContent
import com.example.yumifi1.features.recipe_details.interactor.database.RecipeDao
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeWithContent
import com.example.yumifi1.features.recipe_details.ui.model.Recipe
import com.example.yumifi1.features.recipe_details.ui.model.RecipePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.Base64
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDao,
    private val productDao: ProductDao,
    private val recipeDao: RecipeDao,
    @UserConfig private val userSharedPreferences: SharedPreferences
) {
    private val messageDigest = MessageDigest.getInstance("SHA-256")

    suspend fun getCurrentUserId(): Long = withContext(Dispatchers.IO) {
        userSharedPreferences.getLong(USER_ID_KEY, UNDEFINED_USER_ID)
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
            putLong(USER_ID_KEY, existedUser.id)
        }
        return@withContext Result.success(Unit)
    }

    suspend fun logout() = withContext(Dispatchers.IO) {
        userSharedPreferences.edit(commit = true) {
            putLong(USER_ID_KEY, UNDEFINED_USER_ID)
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

    fun getUserWithContent(
        userId: Long,
    ): Flow<UserWithContent> = combine(
        userDao.getUserWithContent(userId = userId),
        recipeDao.getRecipesWithIngredients(userId = userId)
    ) { userWithContent, recipes ->
        userWithContent to recipes
    }.map { (userWithContext, recipes) ->
        UserWithContent(
            user = userWithContext.user.mapToDomain(),
            recipes = recipes.map { recipeEntity ->
                recipeEntity.mapToDomain(userId)
            },
            comments = userWithContext.comments.map { commentEntity ->
                commentEntity.mapToDomain(userId = userId)
            },
        )
    }

    private fun hashPassword(password: String): String {
        val hashedPassword = messageDigest.digest(password.toByteArray())
        return Base64.getEncoder().encodeToString(hashedPassword)
    }

    private suspend fun RecipeWithContent.mapToDomain(userId: Long): Recipe =
        Recipe(
            id = recipe.id,
            name = recipe.name,
            description = recipe.description,
            ingredients = ingredients.mapNotNull { entity ->
                val productEntity = productDao.getProductById(entity.productId)
                if (productEntity != null) {
                    entity.mapToDomain(product = productEntity)
                } else {
                    null
                }
            },
            comments = comments.map { entity ->
                entity.mapToDomain(userId = userId)
            },
            photos = photos.map { entity ->
                RecipePhoto(
                    id = entity.id,
                    uri = entity.uri,
                )
            }
        )

    private fun IngredientEntity.mapToDomain(
        product: ProductEntity,
    ): Recipe.Ingredient = Recipe.Ingredient(
        id = id,
        product = Product(
            id = product.id,
            name = product.name,
            unit = ProductUnit.valueOf(product.unit),
        ),
        quantity = 1,
    )

    private companion object {
        const val USER_ID_KEY = "USER_ID_KEY"
        const val UNDEFINED_USER_ID = -1L
    }
}