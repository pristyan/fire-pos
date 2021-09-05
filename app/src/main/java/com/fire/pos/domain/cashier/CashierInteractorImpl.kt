package com.fire.pos.domain.cashier

import com.fire.pos.data.repository.cart.CartRepository
import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.model.entity.ProductCartEntity
import com.fire.core.model.Result
import com.fire.pos.model.view.ProductCart
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CashierInteractorImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : CashierInteractor {

    override suspend fun getProductWithCartList(): Result<List<ProductCart>> {
        // get product list
        return when (val result = productRepository.getProductList()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.map {
                    ProductCart(it)
                } ?: emptyList()

                // get cart
                when (val cResult = cartRepository.getCart()) {
                    is Result.Error -> Result.Error(cResult.message)
                    is Result.Success -> {
                        cResult.data?.forEach {
                            list.firstOrNull { p -> p.productId == it.productId }.apply {
                                this?.id = it.id
                                this?.qty = it.qty
                            }
                        }

                        Result.Success(list)
                    }
                }
            }
        }
    }

    override suspend fun addCart(productCart: ProductCart): Result<ProductCart> {
        val request = ProductCartEntity(productCart)
        return when (val result = cartRepository.addCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductCart(result.data))
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