package com.fire.pos.data.repository.cart

import com.fire.pos.data.source.local.cart.CartLocalDataSource
import com.fire.pos.model.db.ProductCartDbEntity
import com.fire.pos.model.entity.ProductCartEntity
import com.fire.core.model.Result
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CartRepositoryImpl @Inject constructor(
    private val cartLocalDataSource: CartLocalDataSource
): CartRepository {

    override suspend fun getCart(): Result<List<ProductCartEntity>> {
        return when (val result = cartLocalDataSource.getCart()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(
                result.data?.map { ProductCartEntity(it) } ?: emptyList()
            )
        }
    }

    override suspend fun addCart(productCartEntity: ProductCartEntity): Result<ProductCartEntity> {
        val request = ProductCartDbEntity(productCartEntity)
        return when (val result = cartLocalDataSource.insertCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductCartEntity(result.data))
        }
    }

    override suspend fun updateCart(productCartEntity: ProductCartEntity): Result<ProductCartEntity> {
        val request = ProductCartDbEntity(productCartEntity)
        return when (val result = cartLocalDataSource.updateCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductCartEntity(result.data))
        }
    }

    override suspend fun deleteCart(productCartEntity: ProductCartEntity): Result<Boolean> {
        val request = ProductCartDbEntity(productCartEntity)
        return when (val result = cartLocalDataSource.deleteCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }

    override suspend fun clearCart(): Result<Boolean> {
        return when (val result = cartLocalDataSource.clearCart()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }


}