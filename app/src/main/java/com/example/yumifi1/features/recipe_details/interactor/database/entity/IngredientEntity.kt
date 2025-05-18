package com.example.yumifi1.features.recipe_details.interactor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity.Companion.INGREDIENT_PRODUCT_ID_COLUMN
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity.Companion.INGREDIENT_RECIPE_ID
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity.Companion.INGREDIENT_USER_OWNER_ID

const val INGREDIENTS_TABLE = "ingredients"

@Entity(
    tableName = INGREDIENTS_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserEntity.USER_ID_COLUMN],
            childColumns = [INGREDIENT_USER_OWNER_ID],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = [RecipeEntity.RECIPE_ID_COLUMN],
            childColumns = [INGREDIENT_RECIPE_ID],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = [ProductEntity.PRODUCT_ID_COLUMN],
            childColumns = [INGREDIENT_PRODUCT_ID_COLUMN],
            onDelete = CASCADE,
        )
    ],
    indices = [
        Index(INGREDIENT_USER_OWNER_ID),
        Index(INGREDIENT_RECIPE_ID),
        Index(INGREDIENT_PRODUCT_ID_COLUMN),
    ]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(INGREDIENT_ID_COLUMN)
    val id: Long? = null,
    @ColumnInfo(INGREDIENT_PRODUCT_ID_COLUMN)
    val productId: Long,
    @ColumnInfo(INGREDIENT_COUNT_COLUMN)
    val count: Int,
    @ColumnInfo(INGREDIENT_USER_OWNER_ID)
    val userOwnerId: Long,
    @ColumnInfo(INGREDIENT_RECIPE_ID)
    val recipeId: Long,
) {
    companion object {
        const val INGREDIENT_ID_COLUMN = "ingredient_id"
        const val INGREDIENT_PRODUCT_ID_COLUMN = "product_id"
        const val INGREDIENT_COUNT_COLUMN = "count"
        const val INGREDIENT_USER_OWNER_ID = "user_owner_id"
        const val INGREDIENT_RECIPE_ID = "recipe_id"
    }
}