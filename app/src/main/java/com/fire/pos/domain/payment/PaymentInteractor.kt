package com.fire.pos.domain.payment

import com.fire.pos.model.response.Result
import com.fire.pos.model.view.ProductCart


/**
 * Created by Chandra.
 **/

interface PaymentInteractor {

    suspend fun getCartList(): Result<List<ProductCart>>

    suspend fun pay(items: List<ProductCart>): Result<Boolean>

}