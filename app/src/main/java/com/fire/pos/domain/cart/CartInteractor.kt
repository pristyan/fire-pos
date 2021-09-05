package com.fire.pos.domain.cart

import com.fire.core.model.Result
import com.fire.pos.model.view.ProductCart


/**
 * Created by Chandra.
 **/

interface CartInteractor {

    suspend fun getCartList(): Result<List<ProductCart>>

    suspend fun updateCart(productCart: ProductCart): Result<ProductCart>

    suspend fun deleteCart(productCart: ProductCart): Result<Boolean>

}