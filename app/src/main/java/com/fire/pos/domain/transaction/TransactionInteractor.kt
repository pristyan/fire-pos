package com.fire.pos.domain.transaction

import com.fire.pos.model.response.Result
import com.fire.pos.model.view.ProductCart


/**
 * Created by Chandra.
 **/

interface TransactionInteractor {

    suspend fun getProductWithCartList(): Result<List<ProductCart>>

    suspend fun addCart(productCart: ProductCart): Result<ProductCart>

    suspend fun updateCart(productCart: ProductCart): Result<ProductCart>

    suspend fun deleteCart(productCart: ProductCart): Result<Boolean>

}