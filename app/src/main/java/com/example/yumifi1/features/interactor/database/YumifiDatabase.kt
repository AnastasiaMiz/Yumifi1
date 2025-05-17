package com.example.yumifi1.features.interactor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yumifi1.features.auth.interactor.database.UserDao
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.product_details.interactor.database.ProductDao
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
    ],
    version = 1
)
abstract class YumifiDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao

    companion object {
        const val DATABASE_NAME = "yumifi_database"
    }
}