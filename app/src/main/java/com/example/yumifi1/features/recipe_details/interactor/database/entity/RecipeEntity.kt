package com.example.yumifi1.features.recipe_details.interactor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity.Companion.COMMENT_RECIPE_OWNER
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity.Companion.INGREDIENT_RECIPE_ID
import com.example.yumifi1.features.recipe_details.interactor.database.entity.PhotoEntity.Companion.PHOTO_RECIPE_ID
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity.Companion.RECIPE_ID_COLUMN
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity.Companion.RECIPE_USER_OWNER_ID

const val RECIPES_TABLE = "recipes"

@Entity(
    tableName = RECIPES_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserEntity.USER_ID_COLUMN],
            childColumns = [RECIPE_USER_OWNER_ID],
            onDelete = CASCADE,
        )
    ],
    indices = [
        Index(RECIPE_USER_OWNER_ID)
    ]
)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(RECIPE_ID_COLUMN)
    val id: Long? = null,
    @ColumnInfo(RECIPE_NAME_COLUMN)
    val name: String,
    @ColumnInfo(RECIPE_DESCRIPTION_COLUMN)
    val description: String,
    @ColumnInfo(RECIPE_USER_OWNER_ID)
    val userOwnerId: Long,
    val photoUrl: String? = null,
) {
    companion object {
        const val RECIPE_ID_COLUMN = "recipe_id"
        const val RECIPE_NAME_COLUMN = "name"
        const val RECIPE_DESCRIPTION_COLUMN = "description"
        const val RECIPE_USER_OWNER_ID = "user_owner_id"
    }
}

/**
 * Загрузка рецепта с ингредиентами
 * (Аналогично можно сделать для загрузки ингредиента вместе с рецептами, где он используется)
 */
data class RecipeWithContent(
    @Embedded
    val recipe: RecipeEntity,
    @Relation(
        parentColumn = RECIPE_ID_COLUMN,
        entityColumn = INGREDIENT_RECIPE_ID,
    )
    val ingredients: List<IngredientEntity>,
    @Relation(
        parentColumn = RECIPE_ID_COLUMN,
        entityColumn = COMMENT_RECIPE_OWNER
    )
    val comments: List<CommentEntity>,
    @Relation(
        parentColumn = RECIPE_ID_COLUMN,
        entityColumn = PHOTO_RECIPE_ID,
    )
    val photos: List<PhotoEntity>,
)