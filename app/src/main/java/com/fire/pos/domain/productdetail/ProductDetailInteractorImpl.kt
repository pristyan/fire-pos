package com.fire.pos.domain.productdetail

import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Product
import com.fire.pos.util.toResultBuilder
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
        return response.toResultBuilder<ProductEntity, Product>()
            .setData { Product(it) }
            .build()
    }

    override suspend fun updateProduct(
        product: Product,
        newImage: File?,
    ): Result<Product> {
        val productEntity = ProductEntity(product)
        val response = productRepository.updateProduct(productEntity, newImage)
        return response.toResultBuilder<ProductEntity, Product>()
            .setData { Product(it) }
            .build()
    }

    override suspend fun deleteProduct(product: Product): Result<Boolean> {
        val response = productRepository.deleteProduct(product.id)
        return response.toResultBuilder<Boolean, Boolean>()
            .setData { response.data ?: false }
            .build()
    }


}