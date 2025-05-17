package com.example.yumifi1.features.product_details.interactor

import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.product_details.interactor.database.ProductDao
import com.example.yumifi1.features.product_details.interactor.database.entity.ProductEntity
import com.example.yumifi1.features.product_details.ui.model.Product
import com.example.yumifi1.features.product_details.ui.model.ProductUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val authRepository: AuthRepository,
) {

    suspend fun saveProduct(
        product: Product,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val isProductExist = productDao.isProductExists(product.name)
        if (isProductExist) {
            return@withContext Result.failure(
                IllegalStateException("Продукт уже существует")
            )
        }
        val productEntity = ProductEntity(
            id = product.id,
            name = product.name,
            unit = product.unit.value,
            userOwnerId = authRepository.getCurrentUserId(),
        )
        productDao.insertProduct(productEntity)
        Result.success(Unit)
    }

    suspend fun getProduct(productId: Int): Result<Product> = withContext(Dispatchers.IO) {
        val productEntity = productDao.getProductById(
            productId = productId
        )
        if (productEntity == null) {
            return@withContext Result.failure(
                IllegalStateException("Продукт не найден")
            )
        }
        val product = Product(
            id = productEntity.id,
            name = productEntity.name,
            unit = ProductUnit.valueOf(productEntity.unit),
        )
        Result.success(product)
    }

    fun getProductsForUserFlow(
        userId: Int
    ): Flow<List<Product>> = productDao.getProductsForUserFlow(
        userId = userId
    ).map { products ->
        products.map { productEntity ->
            Product(
                id = productEntity.id,
                name = productEntity.name,
                unit = ProductUnit.valueOf(productEntity.unit),
            )
        }
    }
}