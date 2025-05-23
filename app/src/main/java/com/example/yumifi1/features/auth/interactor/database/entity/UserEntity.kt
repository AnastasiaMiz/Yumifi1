package com.example.yumifi1.features.auth.interactor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity
import com.example.yumifi1.features.profile.ui.model.User
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeWithIngredients

const val USER_TABLE = "users"

@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USER_ID_COLUMN)
    val id: Long? = null,
    @ColumnInfo(name = USER_EMAIL_COLUMN)
    val email: String,
    @ColumnInfo(name = USER_PASSWORD_COLUMN)
    val password: String
) {
    companion object {
        const val USER_ID_COLUMN = "user_id"
        const val USER_EMAIL_COLUMN = "email"
        const val USER_PASSWORD_COLUMN = "user_password"
    }
}

data class UserWithContentEntity(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = UserEntity.USER_ID_COLUMN,
        entityColumn = RecipeEntity.RECIPE_USER_OWNER_ID,
    )
    val recipes: List<RecipeEntity>,
    @Relation(
        parentColumn = UserEntity.USER_ID_COLUMN,
        entityColumn = CommentEntity.COMMENT_USER_OWNER,
    )
    val comments: List<CommentEntity>,
)

fun UserEntity.mapToDomain(): User = User(
    id = id,
    email = email,
)