package com.example.yumifi1.features.recipe_details.interactor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yumifi1.features.recipe_details.interactor.database.entity.INGREDIENTS_TABLE
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity.Companion.INGREDIENT_ID_COLUMN

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredientEntity: IngredientEntity): Long

    @Query("DELETE FROM $INGREDIENTS_TABLE WHERE $INGREDIENT_ID_COLUMN = :ingredientId")
    suspend fun deleteIngredient(ingredientId: Long)
}