package com.example.yumifi1.features.product_details.interactor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.yumifi1.features.auth.interactor.database.entity.UserEntity
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity.Companion.PRODUCT_USER_OWNER_ID

const val PRODUCTS_TABLE = "products"

@Entity(
    tableName = PRODUCTS_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserEntity.USER_ID_COLUMN],
            childColumns = [PRODUCT_USER_OWNER_ID],
            onDelete = CASCADE,
        )
    ],
    indices = [
        Index(PRODUCT_USER_OWNER_ID)
    ]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PRODUCT_ID_COLUMN)
    val id: Long? = null,
    @ColumnInfo(name = PRODUCT_NAME_COLUMN)
    val name: String,
    @ColumnInfo(name = PRODUCT_UNIT_COLUMN)
    val unit: String,
    @ColumnInfo(name = PRODUCT_USER_OWNER_ID)
    val userOwnerId: Long,
) {
    companion object {
        const val PRODUCT_ID_COLUMN = "product_id"
        const val PRODUCT_NAME_COLUMN = "name"
        const val PRODUCT_UNIT_COLUMN = "unit"
        const val PRODUCT_USER_OWNER_ID = "user_owner_id"
    }
}

/**
 * Entity для получения пользователя со всеми его продуктами
 */
data class UserWithProductsEntity(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = UserEntity.USER_ID_COLUMN,
        entityColumn = ProductEntity.PRODUCT_ID_COLUMN
    )
    val products: List<ProductEntity>
)