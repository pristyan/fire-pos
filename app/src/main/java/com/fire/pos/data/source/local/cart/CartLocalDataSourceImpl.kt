package com.fire.pos.data.source.local.cart

import com.fire.pos.constant.ResponseConstant
import com.fire.pos.data.dao.CartDao
import com.fire.pos.database.AppDatabase
import com.fire.pos.model.db.ProductCartDbEntity
import com.fire.pos.model.response.Result
import java.util.*
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CartLocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase
): CartLocalDataSource {

    private val cartDao: CartDao
        get() = appDatabase.cartDao()

    override suspend fun insertCart(productCartDbEntity: ProductCartDbEntity): Result<ProductCartDbEntity> {
        return try {
            productCartDbEntity.id = UUID.randomUUID().toString()
            cartDao.insert(productCartDbEntity)
            Result.Success(productCartDbEntity)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(e.message ?: ResponseConstant.SOMETHING_GOES_WRONG_SOURCE)
        }
    }

    override suspend fun updateCart(productCartDbEntity: ProductCartDbEntity): Result<ProductCartDbEntity> {
        return try {
            cartDao.update(productCartDbEntity)
            Result.Success(productCartDbEntity)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(e.message ?: ResponseConstant.SOMETHING_GOES_WRONG_SOURCE)
        }
    }

    override suspend fun clearCart(): Result<Boolean> {
        return try {
            cartDao.clearCart()
            Result.Success(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(e.message ?: ResponseConstant.SOMETHING_GOES_WRONG_SOURCE)
        }
    }

    override suspend fun deleteCart(productCartDbEntity: ProductCartDbEntity): Result<Boolean> {
        return try {
            cartDao.delete(productCartDbEntity)
            Result.Success(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(e.message ?: ResponseConstant.SOMETHING_GOES_WRONG_SOURCE)
        }
    }

    override suspend fun getCart(): Result<List<ProductCartDbEntity>> {
        return try {
            Result.Success(cartDao.getAll())
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(e.message ?: ResponseConstant.SOMETHING_GOES_WRONG_SOURCE)
        }
    }
}