package com.example.yumifi1.features.comment.interactor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity.Companion.COMMENT_RECIPE_OWNER
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity.Companion.COMMENT_USER_OWNER
import com.example.yumifi1.features.comment.ui.model.Comment
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity

const val COMMENTS_TABLE = "comments"

@Entity(
    tableName = COMMENTS_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserEntity.USER_ID_COLUMN],
            childColumns = [COMMENT_USER_OWNER],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = [RecipeEntity.RECIPE_ID_COLUMN],
            childColumns = [COMMENT_RECIPE_OWNER],
            onDelete = CASCADE,
        )
    ],
    indices = [
        Index(COMMENT_USER_OWNER),
        Index(COMMENT_RECIPE_OWNER)
    ]
)
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(COMMENT_ID)
    val id: Long? = null,
    @ColumnInfo(COMMENT_TEXT)
    val text: String,
    @ColumnInfo(COMMENT_USER_OWNER)
    val userOwnerId: Long,
    @ColumnInfo(COMMENT_RECIPE_OWNER)
    val recipeOwnerId: Long,
) {
    companion object {
        const val COMMENT_ID = "comment_id"
        const val COMMENT_TEXT = "text"
        const val COMMENT_USER_OWNER = "user_owner_id"
        const val COMMENT_RECIPE_OWNER = "recipe_owner_id"
    }
}

fun CommentEntity.mapToDomain(userId: Long): Comment = Comment(
    id = id,
    text = text,
    isMy = userOwnerId == userId
)