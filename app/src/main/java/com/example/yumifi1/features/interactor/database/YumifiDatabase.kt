package com.example.yumifi1.features.interactor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yumifi1.features.auth.interactor.database.UserDao
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class YumifiDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "yumifi_database"
    }
}