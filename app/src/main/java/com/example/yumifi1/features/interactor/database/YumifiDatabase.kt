package com.example.yumifi1.features.interactor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yumifi1.features.auth.interactor.database.UserDao
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.comment.interactor.database.CommentDao
import com.example.yumifi1.features.comment.interactor.database.entity.CommentEntity
import com.example.yumifi1.features.product_details.interactor.database.ProductDao
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity
import com.example.yumifi1.features.recipe_details.interactor.database.IngredientDao
import com.example.yumifi1.features.recipe_details.interactor.database.PhotoDao
import com.example.yumifi1.features.recipe_details.interactor.database.RecipeDao
import com.example.yumifi1.features.recipe_details.interactor.database.entity.IngredientEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.PhotoEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.RecipeEntity

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        RecipeEntity::class,
        IngredientEntity::class,
        CommentEntity::class,
        PhotoEntity::class,
    ],
    version = 1
)
abstract class YumifiDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun commentDao(): CommentDao
    abstract fun photoDao(): PhotoDao

    companion object {
        const val DATABASE_NAME = "yumifi_database"
    }
}