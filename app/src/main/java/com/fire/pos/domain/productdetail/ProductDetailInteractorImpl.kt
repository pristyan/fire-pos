package com.fire.pos.domain.productdetail

import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.model.entity.ProductEntity
import com.fire.core.model.Result
import com.fire.pos.model.view.Product
import com.fire.core.util.FirestoreIdGenerator
import java.io.File
import java.util.*
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
            id = FirestoreIdGenerator.generateProductId(),
            name = name,
            price = price,
            stock = stock,
            image = null,
            imageFileName = null,
            categoryId = "",
            sku = "",
            createdAt = Date()
        )

        return when (val response = productRepository.addProduct(productEntity, image)) {
            is Result.Error -> Result.Error(response.message)
            is Result.Success -> Result.Success(Product(response.data))
        }
    }

    override suspend fun updateProduct(
        product: Product,
        newImage: File?,
    ): Result<Product> {
        val productEntity = ProductEntity(product)
        return when (val response = productRepository.updateProduct(productEntity, newImage)) {
            is Result.Error -> Result.Error(response.message)
            is Result.Success -> Result.Success(Product(response.data))
        }
    }

    override suspend fun deleteProduct(product: Product): Result<Boolean> {
        return when (val response = productRepository.deleteProduct(product.id)) {
            is Result.Error -> Result.Error(response.message)
            is Result.Success -> Result.Success(response.data ?: false)
        }
    }


}