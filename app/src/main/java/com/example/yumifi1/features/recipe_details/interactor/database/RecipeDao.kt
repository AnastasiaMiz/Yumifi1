package com.example.yumifi1.features.recipe_details.interactor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.yumifi1.features.product_details.interactor.database.entity.PRODUCTS_TABLE
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity.Companion.PRODUCT_ID_COLUMN
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity.Companion.PRODUCT_NAME_COLUMN
import com.example.yumifi1.features.recipe_details.interactor.database.entity.INGREDIENTS_TABLE
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity.Companion.INGREDIENT_PRODUCT_ID_COLUMN
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity.Companion.INGREDIENT_RECIPE_ID
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RECIPES_TABLE
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity.Companion.RECIPE_ID_COLUMN
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity.Companion.RECIPE_USER_OWNER_ID
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeWithContent
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetRecipe(recipe: RecipeEntity): Long

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM $RECIPES_TABLE WHERE $RECIPE_ID_COLUMN = :recipeId")
    suspend fun deleteRecipe(recipeId: Long)

    @Query("SELECT * FROM $RECIPES_TABLE")
    fun getAllRecipes(): Flow<List<RecipeWithContent>>

    @Query("SELECT * FROM $RECIPES_TABLE WHERE $RECIPE_USER_OWNER_ID = :userId")
    fun getRecipesForUser(userId: Long): Flow<List<RecipeEntity>>

    @Transaction
    @Query("SELECT * FROM $RECIPES_TABLE WHERE $RECIPE_ID_COLUMN = :recipeId")
    fun getRecipeWithContent(recipeId: Long): Flow<RecipeWithContent>

    @Transaction
    @Query("SELECT * FROM $RECIPES_TABLE WHERE $RECIPE_USER_OWNER_ID = :userId")
    fun getRecipesWithIngredients(userId: Long): Flow<List<RecipeWithContent>>

    @Transaction
    @Query("""
        SELECT DISTINCT r.* FROM $RECIPES_TABLE AS r
        JOIN $INGREDIENTS_TABLE AS i ON r.$RECIPE_ID_COLUMN = i.$INGREDIENT_RECIPE_ID
        WHERE i.$INGREDIENT_PRODUCT_ID_COLUMN IN (:productsId)
    """)
    fun getRecipesWithIngredients(
        productsId: List<Long>
    ): Flow<List<RecipeWithContent>>

    @Transaction
    @Query("""
        SELECT DISTINCT r.* FROM $RECIPES_TABLE AS r
        INNER JOIN $INGREDIENTS_TABLE AS i ON r.$RECIPE_ID_COLUMN = i.$INGREDIENT_RECIPE_ID
        INNER JOIN $PRODUCTS_TABLE p ON i.$INGREDIENT_PRODUCT_ID_COLUMN = p.$PRODUCT_ID_COLUMN
        WHERE p.$PRODUCT_NAME_COLUMN IN (:productsName)
    """)
    fun getRecipesWithContent(
        productsName: List<String>
    ): Flow<List<RecipeWithContent>>

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM $RECIPES_TABLE WHERE $RECIPE_ID_COLUMN = :recipeId AND $RECIPE_USER_OWNER_ID = :userId
        )
    """)
    suspend fun isRecipeOwnedByUser(userId: Long, recipeId: Long): Boolean
}