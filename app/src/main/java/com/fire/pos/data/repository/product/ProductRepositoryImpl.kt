package com.fire.pos.data.repository.product

import com.fire.pos.data.source.local.cart.CartLocalDataSource
import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.model.db.ProductCartDbEntity
import com.fire.pos.model.entity.ProductCartEntity
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.Result
import java.io.File
import java.util.*
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val cartLocalDataSource: CartLocalDataSource
) : ProductRepository {

    override suspend fun getProductList(): Result<List<ProductEntity>> {
        return when (val result = productRemoteDataSource.getProductList()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.documents?.map { doc ->
                    ProductEntity(doc.id, doc)
                } ?: emptyList()
                Result.Success(list)
            }
        }
    }

    override suspend fun getProductWithCart(): Result<List<ProductCartEntity>> {
        return when (val result = productRemoteDataSource.getProductList()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.documents?.map {
                    ProductCartEntity(it.id, it)
                } ?: emptyList()

                when (val cartResult = cartLocalDataSource.getCart()) {
                    is Result.Error -> Result.Error(cartResult.message)
                    is Result.Success -> {
                        cartResult.data?.forEach {
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

    override suspend fun getProductId(id: String): Result<ProductEntity> {
        return when (val result = productRemoteDataSource.getProductById(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductEntity(id, result.data))
        }
    }

    override suspend fun addProduct(
        productEntity: ProductEntity,
        file: File
    ): Result<ProductEntity> {

        // upload image
        return when (val imageResult = productRemoteDataSource.uploadImage(file)) {
            is Result.Error -> Result.Error(imageResult.message)
            is Result.Success -> {

                // set image attributes
                productEntity.image = imageResult.data?.toString()
                productEntity.imageFileName = file.name

                // add to fire store
                when (val remoteResult = productRemoteDataSource.addProduct(productEntity)) {
                    is Result.Error -> Result.Error(remoteResult.message)
                    is Result.Success -> {

                        // set product id from fire store
                        val docRefs = remoteResult.data
                        productEntity.id = docRefs?.id
                        Result.Success(productEntity)
                    }
                }
            }
        }
    }

    override suspend fun updateProduct(
        productEntity: ProductEntity,
        file: File?
    ): Result<ProductEntity> {

        suspend fun update(entity: ProductEntity): Result<ProductEntity> {
            return when (val remoteResult = productRemoteDataSource.updateProduct(entity)) {
                is Result.Error -> Result.Error(remoteResult.message)
                is Result.Success -> Result.Success(entity)
            }
        }

        return if (file == null) {
            update(productEntity)
        } else {

            // upload image
            return when (val imageResult = productRemoteDataSource.uploadImage(file)) {
                is Result.Error -> Result.Error(imageResult.message)
                is Result.Success -> {

                    // delete previous image
                    productRemoteDataSource.deleteImage(productEntity.imageFileName as String)

                    // set image attributes
                    productEntity.image = imageResult.data.toString()
                    productEntity.imageFileName = file.name

                    // update data
                    update(productEntity)
                }
            }
        }
    }

    override suspend fun updateProductStock(items: List<ProductCartEntity>): Result<Boolean> {
        return when (val result = productRemoteDataSource.updateProductStock(items)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(true)
        }
    }

    override suspend fun deleteProduct(id: String): Result<Boolean> {
        return when (val result = productRemoteDataSource.deleteProduct(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(true)
        }
    }

    override suspend fun addProductToCart(productCartEntity: ProductCartEntity): Result<ProductCartEntity> {
        val request = ProductCartDbEntity(productCartEntity)
        return when (val result = cartLocalDataSource.insertCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductCartEntity(result.data))
        }
    }

    override suspend fun updateCart(productCartEntity: ProductCartEntity): Result<ProductCartEntity> {
        val request = ProductCartDbEntity(productCartEntity)
        return when (val result = cartLocalDataSource.updateCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductCartEntity(result.data))
        }
    }

    override suspend fun deleteProductFromCart(productCartEntity: ProductCartEntity): Result<Boolean> {
        val request = ProductCartDbEntity(productCartEntity)
        return when (val result = cartLocalDataSource.deleteCart(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }

    override suspend fun clearCart(): Result<Boolean> {
        return when (val result = cartLocalDataSource.clearCart()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }

    override suspend fun getCart(): Result<List<ProductCartEntity>> {
        return when (val result = cartLocalDataSource.getCart()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(
                result.data?.map { ProductCartEntity(it) } ?: emptyList()
            )
        }
    }

}