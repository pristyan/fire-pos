package com.fire.pos.domain.interactor.productlist

import com.fire.pos.data.constant.ResponseConstant
import com.fire.pos.data.response.Result
import com.fire.pos.data.view.Product
import com.fire.pos.domain.repository.remote.ProductRemoteDataSource
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductListInteractorImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductListInteractor {

    override suspend fun getProducts(): Result<List<Product>> {
        val response = productRemoteDataSource.getProductList()
        return if (response.isSuccess) {
            val productList = response.data?.documents?.map { Product(it) }
            Result.Success(productList ?: emptyList())
        } else {
            Result.Error(
                response.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG
            )
        }
    }
}