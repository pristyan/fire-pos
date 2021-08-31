package com.fire.pos.domain.transaction

import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.model.entity.ProductCartEntity
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.ProductCart
import java.util.*
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionInteractorImpl @Inject constructor(
    private val productRepository: ProductRepository
) : TransactionInteractor {

    override suspend fun getProductList(): Result<List<ProductCart>> {
        return when (val result = productRepository.getProductWithCart()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.map { ProductCart(it) } ?: emptyList()
                Result.Success(list)
            }
        }
    }

    override suspend fun addCart(productCart: ProductCart): Result<ProductCart> {
        val request = ProductCartEntity(productCart)
        return when (val result = productRepository.addProductToCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductCart(result.data))
        }
    }

    override suspend fun updateCart(productCart: ProductCart): Result<ProductCart> {
        val request = ProductCartEntity(productCart)
        return when (val result = productRepository.updateCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductCart(result.data))
        }
    }

    override suspend fun deleteCart(productCart: ProductCart): Result<Boolean> {
        val request = ProductCartEntity(productCart)
        return when (val result = productRepository.deleteProductFromCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }
}