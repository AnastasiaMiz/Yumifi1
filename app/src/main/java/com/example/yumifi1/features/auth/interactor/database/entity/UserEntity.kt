package com.example.yumifi1.features.auth.interactor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val USER_TABLE = "users"

@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int? = null,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "user_password")
    val password: String
)