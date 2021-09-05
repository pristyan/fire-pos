package com.fire.pos.data.source.local.cart

import com.fire.pos.model.db.ProductCartDbEntity
import com.fire.core.model.Result


/**
 * Created by Chandra.
 **/

interface CartLocalDataSource {

    suspend fun insertCart(productCartDbEntity: ProductCartDbEntity): Result<ProductCartDbEntity>

    suspend fun updateCart(productCartDbEntity: ProductCartDbEntity): Result<ProductCartDbEntity>

    suspend fun clearCart(): Result<Boolean>

    suspend fun deleteCart(productCartDbEntity: ProductCartDbEntity): Result<Boolean>

    suspend fun getCart(): Result<List<ProductCartDbEntity>>

}