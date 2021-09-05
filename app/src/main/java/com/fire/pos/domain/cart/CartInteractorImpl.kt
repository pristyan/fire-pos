package com.fire.pos.domain.cart

import com.fire.pos.data.repository.cart.CartRepository
import com.fire.pos.model.entity.ProductCartEntity
import com.fire.core.model.Result
import com.fire.pos.model.view.ProductCart
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CartInteractorImpl @Inject constructor(
    private val cartRepository: CartRepository
): CartInteractor {

    override suspend fun getCartList(): Result<List<ProductCart>> {
        return when (val result = cartRepository.getCart()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.map { ProductCart(it) } ?: emptyList()
                Result.Success(list)
            }
        }
    }

    override suspend fun updateCart(productCart: ProductCart): Result<ProductCart> {
        val request = ProductCartEntity(productCart)
        return when (val result = cartRepository.updateCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductCart(result.data))
        }
    }

    override suspend fun deleteCart(productCart: ProductCart): Result<Boolean> {
        val request = ProductCartEntity(productCart)
        return when (val result = cartRepository.deleteCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }
}