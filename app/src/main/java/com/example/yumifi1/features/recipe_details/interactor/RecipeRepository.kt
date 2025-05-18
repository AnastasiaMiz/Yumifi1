package com.example.yumifi1.features.recipe_details.interactor

import androidx.room.Transaction
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.recipe_details.interactor.database.IngredientDao
import com.example.yumifi1.features.recipe_details.interactor.database.RecipeDao
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity
import com.example.yumifi1.features.recipes.ui.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
//    private val recipeIngredientsCrossRefDao: RecipeIngredientsCrossRefDao,
    private val authRepository: AuthRepository,
) {

    @Transaction
    suspend fun createRecipeWithIngredients(
        recipe: Recipe,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val userId = authRepository.getCurrentUserId()
        val recipeEntity = RecipeEntity(
            name = recipe.name,
            description = recipe.description,
            userOwnerId = userId,
        )
        val recipeId = recipeDao.insetRecipe(recipeEntity)

        recipe.ingredients.forEach { ingredient ->
            val productId = ingredient.product.id
            if (productId != null) {
                val ingredientEntity = IngredientEntity(
                    productId = productId,
                    count = ingredient.quantity,
                    userOwnerId = userId,
                    recipeId = recipeId,
                )
                ingredientDao.insertIngredient(ingredientEntity)
            }
        }
        Result.success(Unit)
    }
}