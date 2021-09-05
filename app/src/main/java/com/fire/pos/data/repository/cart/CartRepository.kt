package com.fire.pos.data.repository.cart

import com.fire.pos.model.entity.ProductCartEntity
import com.fire.core.model.Result


/**
 * Created by Chandra.
 **/

interface CartRepository {

    suspend fun getCart(): Result<List<ProductCartEntity>>

    suspend fun addCart(productCartEntity: ProductCartEntity): Result<ProductCartEntity>

    suspend fun updateCart(productCartEntity: ProductCartEntity): Result<ProductCartEntity>

    suspend fun deleteCart(productCartEntity: ProductCartEntity): Result<Boolean>

    suspend fun clearCart(): Result<Boolean>
}