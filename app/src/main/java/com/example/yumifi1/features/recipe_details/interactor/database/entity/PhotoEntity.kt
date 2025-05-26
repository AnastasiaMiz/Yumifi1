package com.example.yumifi1.features.recipe_details.interactor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.PhotoEntity.Companion.PHOTO_RECIPE_ID
import com.example.yumifi1.features.recipe_details.interactor.database.entity.PhotoEntity.Companion.PHOTO_USER_ID

const val PHOTOS_TABLE = "photos"

@Entity(
    tableName = PHOTOS_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserEntity.USER_ID_COLUMN],
            childColumns = [PHOTO_USER_ID],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = [RecipeEntity.RECIPE_ID_COLUMN],
            childColumns = [PHOTO_RECIPE_ID],
            onDelete = CASCADE,
        ),
    ],
    indices = [
        Index(PHOTO_USER_ID),
        Index(PHOTO_RECIPE_ID),
    ]
)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(PHOTO_ID)
    val id: Long? = null,
    @ColumnInfo(PHOTO_URI)
    val uri: String,
    @ColumnInfo(PHOTO_USER_ID)
    val userId: Long,
    @ColumnInfo(PHOTO_RECIPE_ID)
    val recipeId: Long,
) {
    companion object {
        const val PHOTO_ID = "photo_id"
        const val PHOTO_URI = "uri"
        const val PHOTO_USER_ID = "user_id"
        const val PHOTO_RECIPE_ID = "recipe_id"
    }
}