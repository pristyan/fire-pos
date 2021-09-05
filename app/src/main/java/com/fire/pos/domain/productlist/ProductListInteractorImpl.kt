package com.fire.pos.domain.productlist

import com.fire.pos.data.repository.product.ProductRepository
import com.fire.core.model.Result
import com.fire.pos.model.view.Product
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductListInteractorImpl @Inject constructor(
    private val productRepository: ProductRepository
) : ProductListInteractor {

    override suspend fun getProducts(): Result<List<Product>> {
        return when (val response = productRepository.getProductList()) {
            is Result.Error -> Result.Error(response.message)
            is Result.Success -> Result.Success(
                response.data?.map { Product(it) } ?: emptyList()
            )
        }
    }
}