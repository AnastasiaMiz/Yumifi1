package com.example.yumifi1.features.recipe_details.interactor.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RECIPES_TABLE
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity.Companion.RECIPE_ID_COLUMN
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity.Companion.RECIPE_USER_OWNER_ID
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetRecipe(recipe: RecipeEntity): Long

    @Query("DELETE FROM $RECIPES_TABLE WHERE $RECIPE_ID_COLUMN = :recipeId")
    suspend fun deleteRecipe(recipeId: Long)

    @Query("SELECT * FROM $RECIPES_TABLE WHERE $RECIPE_USER_OWNER_ID = :userId")
    fun getRecipesForUser(userId: Long): Flow<List<RecipeEntity>>

    @Transaction
    @Query("SELECT * FROM $RECIPES_TABLE WHERE $RECIPE_ID_COLUMN = :recipeId")
    fun getRecipeWithIngredients(recipeId: Long): Flow<RecipeWithIngredients>
}