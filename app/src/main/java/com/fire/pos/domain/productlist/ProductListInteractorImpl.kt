package com.fire.pos.domain.productlist

import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Product
import com.fire.pos.util.getErrorMessage
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductListInteractorImpl @Inject constructor(
    private val productRepository: ProductRepository
) : ProductListInteractor {

    override suspend fun getProducts(): Result<List<Product>> {
        val response = productRepository.getProductList()
        return if (response.isSuccess) {
            val list = response.data?.map { Product(it) } ?: emptyList()
            Result.Success(list)
        } else {
            Result.Error(response.getErrorMessage())
        }
    }
}