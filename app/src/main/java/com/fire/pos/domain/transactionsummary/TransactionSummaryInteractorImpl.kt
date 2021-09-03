package com.fire.pos.domain.transactionsummary

import com.fire.pos.data.repository.cart.CartRepository
import com.fire.pos.model.entity.ProductCartEntity
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.ProductCart
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionSummaryInteractorImpl @Inject constructor(
    private val cartRepository: CartRepository
): TransactionSummaryInteractor {

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