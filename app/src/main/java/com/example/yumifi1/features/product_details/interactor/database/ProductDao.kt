package com.example.yumifi1.features.product_details.interactor.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yumifi1.features.product_details.interactor.database.entity.PRODUCTS_TABLE
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity.Companion.PRODUCT_ID_COLUMN
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity.Companion.PRODUCT_NAME_COLUMN
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity.Companion.USER_OWNER_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM $PRODUCTS_TABLE WHERE $PRODUCT_NAME_COLUMN = :name COLLATE NOCASE
        )
    """)
    suspend fun isProductExists(name: String): Boolean

    @Query("SELECT * FROM $PRODUCTS_TABLE WHERE $PRODUCT_ID_COLUMN = :productId LIMIT 1")
    suspend fun getProductById(productId: Int): ProductEntity?

    @Query("SELECT * FROM $PRODUCTS_TABLE WHERE $USER_OWNER_ID = :userId")
    fun getProductsForUserFlow(userId: Int): Flow<List<ProductEntity>>

    @Query("DELETE FROM $PRODUCTS_TABLE WHERE $PRODUCT_ID_COLUMN = :productId")
    suspend fun deleteProduct(productId: Int)
}