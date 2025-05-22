package com.example.yumifi1.features.recipe_details.interactor

import androidx.room.Transaction
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.comment.interactor.database.entity.mapToDomain
import com.example.yumifi1.features.product_details.interactor.database.ProductDao
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity
import com.example.yumifi1.features.product_details.ui.model.Product
import com.example.yumifi1.features.product_details.ui.model.ProductUnit
import com.example.yumifi1.features.recipe_details.interactor.database.IngredientDao
import com.example.yumifi1.features.recipe_details.interactor.database.RecipeDao
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeWithIngredients
import com.example.yumifi1.features.recipe_details.ui.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val authRepository: AuthRepository,
    private val productDao: ProductDao,
) {

    @Transaction
    suspend fun createRecipeWithIngredients(
        recipe: Recipe,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val userId = authRepository.getCurrentUserId()
        val recipeEntity = RecipeEntity(
            id = recipe.id,
            name = recipe.name,
            description = recipe.description,
            userOwnerId = userId,
        )
        val recipeId = if (recipeEntity.id == null) {
            recipeDao.insetRecipe(recipeEntity)
        } else {
            recipeDao.updateRecipe(recipeEntity)
            recipeEntity.id
        }

        recipe.ingredients.forEach { ingredient ->
            val productId = ingredient.product.id
            if (productId != null) {
                val ingredientEntity = IngredientEntity(
                    id = ingredient.id,
                    productId = productId,
                    count = ingredient.quantity,
                    userOwnerId = userId,
                    recipeId = recipeId,
                )
                if (ingredientEntity.id == null) {
                    ingredientDao.insertIngredient(ingredientEntity)
                } else {
                    ingredientDao.updateIngredient(ingredientEntity)
                }
            }
        }
        Result.success(Unit)
    }

    @Transaction
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes().map { recipes ->
        val userId = authRepository.getCurrentUserId()
        recipes.map { entity -> entity.mapToDomain(userId) }
    }

    @Transaction
    fun getRecipes(userId: Long): Flow<List<Recipe>> = recipeDao.getRecipesWithIngredients(userId)
        .map { recipesWithIngredients ->
            val userId = authRepository.getCurrentUserId()
            recipesWithIngredients.map { entity ->
                entity.mapToDomain(userId)
            }
        }

    suspend fun deleteRecipe(recipeId: Long) = withContext(Dispatchers.IO) {
        recipeDao.deleteRecipe(recipeId)
    }

    @Transaction
    suspend fun deleteIngredients(ingredientsId: List<Long>) = withContext(Dispatchers.IO) {
        ingredientsId.forEach { id ->
            ingredientDao.deleteIngredient(id)
        }
    }

    @Transaction
    fun getRecipeWithIngredients(
        recipeId: Long
    ): Flow<Recipe> = recipeDao.getRecipeWithIngredients(recipeId)
        .map { recipeWithIngredients ->
            val userId = authRepository.getCurrentUserId()
            recipeWithIngredients.mapToDomain(userId)
        }

    suspend fun getRecipes(
        productsId: List<Long>
    ) = withContext(Dispatchers.IO) {
        val userId = authRepository.getCurrentUserId()
        recipeDao.getRecipesWithIngredients(
            productsId = productsId,
        ).map { recipesWithIngredients ->
            recipesWithIngredients.map { entity ->
                entity.mapToDomain(userId = userId)
            }
        }
    }

    private suspend fun RecipeWithIngredients.mapToDomain(userId: Long): Recipe =
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
            }
        )

    suspend fun isRecipeOwnerByUser(
        userId: Long,
        recipeId: Long
    ): Boolean = withContext(Dispatchers.IO) {
        recipeDao.isRecipeOwnedByUser(
            userId = userId,
            recipeId = recipeId,
        )
    }

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
}