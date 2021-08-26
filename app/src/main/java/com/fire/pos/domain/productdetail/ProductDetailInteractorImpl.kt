package com.fire.pos.domain.productdetail

import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Product
import com.fire.pos.util.getErrorMessage
import java.io.File
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductDetailInteractorImpl @Inject constructor(
    private val productRepository: ProductRepository
) : ProductDetailInteractor {

    override suspend fun addProduct(
        name: String,
        price: Long,
        stock: Long,
        image: File
    ): Result<Product> {
        val productEntity = ProductEntity(
            id = null,
            name = name,
            price = price,
            stock = stock,
            image = null,
            imageFileName = null
        )
        val response = productRepository.addProduct(productEntity, image)
        return if (response.isSuccess) {
            Result.Success(Product(response.data))
        } else {
            Result.Error(response.getErrorMessage())
        }
    }

    override suspend fun updateProduct(
        product: Product,
        newImage: File?,
    ): Result<Product> {
        val productEntity = ProductEntity(product)
        val response = productRepository.updateProduct(productEntity, newImage)
        return if (response.isSuccess) {
            Result.Success(Product(response.data))
        } else {
            Result.Error(response.getErrorMessage())
        }
    }

    override suspend fun deleteProduct(product: Product): Result<Boolean> {
        val response = productRepository.deleteProduct(product.id)
        return if (response.isSuccess) {
            Result.Success(response.data ?: false)
        } else {
            Result.Error(response.getErrorMessage())
        }
    }


}