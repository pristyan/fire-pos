package com.fire.pos.domain.cashier

import com.fire.core.model.Result
import com.fire.pos.model.view.ProductCart


/**
 * Created by Chandra.
 **/

interface CashierInteractor {

    suspend fun getProductWithCartList(): Result<List<ProductCart>>

    suspend fun addCart(productCart: ProductCart): Result<ProductCart>

    suspend fun updateCart(productCart: ProductCart): Result<ProductCart>

    suspend fun deleteCart(productCart: ProductCart): Result<Boolean>

}