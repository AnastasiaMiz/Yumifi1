package com.example.yumifi1.features.recipe_details.interactor

import androidx.room.Transaction
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.product_details.interactor.ProductRepository
import com.example.yumifi1.features.product_details.interactor.database.ProductDao
import com.example.yumifi1.features.product_details.ui.model.Product
import com.example.yumifi1.features.product_details.ui.model.ProductUnit
import com.example.yumifi1.features.recipe_details.interactor.database.IngredientDao
import com.example.yumifi1.features.recipe_details.interactor.database.RecipeDao
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity
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
//    private val recipeIngredientsCrossRefDao: RecipeIngredientsCrossRefDao,
    private val authRepository: AuthRepository,
    private val productDao: ProductDao,
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
                    id = ingredient.id,
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

    @Transaction
    fun getRecipes(userId: Long): Flow<List<Recipe>> = recipeDao.getRecipesWithIngredients(userId)
        .map { recipesWithIngredients ->
            recipesWithIngredients.map { entity ->
                Recipe(
                    id = entity.recipe.id,
                    name = entity.recipe.name,
                    description = entity.recipe.description,
                    ingredients = entity.ingredients.mapNotNull { entity ->
                        val productEntity = productDao.getProductById(entity.productId)
                        if (productEntity != null) {
                            Recipe.Ingredient(
                                id = entity.id,
                                product = Product(
                                    id = productEntity.id,
                                    name = productEntity.name,
                                    unit = ProductUnit.valueOf(productEntity.unit),
                                ),
                                quantity = 1,
                            )
                        } else {
                            null
                        }
                    },
                )
            }
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
            Recipe(
                id = recipeWithIngredients.recipe.id,
                name = recipeWithIngredients.recipe.name,
                description = recipeWithIngredients.recipe.description,
                ingredients = recipeWithIngredients.ingredients.mapNotNull { entity ->
                    val productEntity = productDao.getProductById(entity.productId)
                    if (productEntity != null) {
                        Recipe.Ingredient(
                            id = entity.id,
                            product = Product(
                                id = productEntity.id,
                                name = productEntity.name,
                                unit = ProductUnit.valueOf(productEntity.unit),
                            ),
                            quantity = 1,
                        )
                    } else {
                        null
                    }
                },
            )
        }
}