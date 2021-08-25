package com.fire.pos.data.source.local.product

import android.content.SharedPreferences
import com.fire.pos.data.dao.ProductDao
import com.fire.pos.database.AppDatabase
import com.fire.pos.model.db.ProductDbEntity
import com.fire.pos.model.entity.ProductEntity
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val appDatabase: AppDatabase
): ProductLocalDataSource {

    private val productDao: ProductDao
        get() = appDatabase.productDao()

    companion object {
        private const val PRODUCT_LAST_FETCH = "product_last_fetch"
        private const val MIN_FETCH_TIME = 10_000L // 10s
    }

    override fun setLastFetch(data: Long) {
        sharedPreferences.edit().putLong(PRODUCT_LAST_FETCH, data).apply()
    }

    override fun needFetchRemote(): Boolean {
        val lastFetch = sharedPreferences.getLong(PRODUCT_LAST_FETCH, 0L)
        return System.currentTimeMillis() - lastFetch >= MIN_FETCH_TIME
    }

    override suspend fun getProductList(): Result<List<ProductDbEntity>> {
        return try {
            Result.success(productDao.getProducts())
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: String): Result<ProductDbEntity> {
        return try {
            val product = productDao.getProductById(id).firstOrNull()
            if (product == null) Result.failure(Exception("Product not found"))
            else Result.success(product)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertProducts(list: List<ProductEntity>): Result<Boolean> {
        return try {
            productDao.insertProducts(list.map { ProductDbEntity(it) })
            Result.success(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertProduct(productEntity: ProductEntity): Result<Boolean> {
        return try {
            productDao.insertProduct(ProductDbEntity(productEntity))
            Result.success(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(productEntity: ProductEntity): Result<Boolean> {
        return try {
            productDao.updateProduct(ProductDbEntity(productEntity))
            Result.success(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(id: String): Result<Boolean> {
        return try {
            productDao.deleteProductById(id)
            Result.success(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}