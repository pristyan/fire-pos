package com.fire.pos.domain.productlist

import com.fire.pos.constant.ResponseConstant
import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Product
import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.util.toResultBuilder
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductListInteractorImpl @Inject constructor(
    private val productRepository: ProductRepository
) : ProductListInteractor {

    override suspend fun getProducts(): Result<List<Product>> {
        val response = productRepository.getProductList()
        return response.toResultBuilder<List<ProductEntity>, List<Product>>()
            .setData {
                it?.map { data -> Product(data) } ?: emptyList()
            }
            .build()
    }
}