package com.example.yumifi1.features.interactor.di

import android.content.Context
import androidx.room.Room
import com.example.yumifi1.features.auth.interactor.database.UserDao
import com.example.yumifi1.features.comment.interactor.database.CommentDao
import com.example.yumifi1.features.interactor.database.YumifiDatabase
import com.example.yumifi1.features.product_details.interactor.database.ProductDao
import com.example.yumifi1.features.recipe_details.interactor.database.IngredientDao
import com.example.yumifi1.features.recipe_details.interactor.database.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): YumifiDatabase = Room.databaseBuilder(
        appContext,
        YumifiDatabase::class.java,
        YumifiDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideUserDao(
        database: YumifiDatabase,
    ): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideProductDao(
        database: YumifiDatabase,
    ): ProductDao = database.productDao()

    @Provides
    @Singleton
    fun provideRecipeDao(
        database: YumifiDatabase,
    ): RecipeDao = database.recipeDao()

    @Provides
    @Singleton
    fun provideIngredientDao(
        database: YumifiDatabase,
    ): IngredientDao = database.ingredientDao()

    @Provides
    @Singleton
    fun provideCommentDao(
        database: YumifiDatabase,
    ): CommentDao = database.commentDao()
}