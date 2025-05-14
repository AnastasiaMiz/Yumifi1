package com.example.yumifi1.features.auth.interactor.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yumifi1.features.auth.interactor.database.entity.USER_TABLE
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM $USER_TABLE WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): UserEntity?
}