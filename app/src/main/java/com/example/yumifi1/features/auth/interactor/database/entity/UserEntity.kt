package com.example.yumifi1.features.auth.interactor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val USER_TABLE = "users"

@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USER_ID_COLUMN)
    val id: Int? = null,
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