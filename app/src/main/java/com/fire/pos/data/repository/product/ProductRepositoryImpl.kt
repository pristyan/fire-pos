package com.fire.pos.data.repository.product

import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.model.entity.ProductCartEntity
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.Result
import java.io.File
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource
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

    private suspend fun executeUpdate(entity: ProductEntity): Result<ProductEntity> {
        return when (val remoteResult = productRemoteDataSource.updateProduct(entity)) {
            is Result.Error -> Result.Error(remoteResult.message)
            is Result.Success -> Result.Success(entity)
        }
    }

    override suspend fun updateProduct(
        productEntity: ProductEntity,
        file: File?
    ): Result<ProductEntity> {
        return when (file) {
            null -> executeUpdate(productEntity)
            else -> {
                // upload image
                when (val imageResult = productRemoteDataSource.uploadImage(file)) {
                    is Result.Error -> Result.Error(imageResult.message)
                    is Result.Success -> {

                        // delete previous image
                        productRemoteDataSource.deleteImage(productEntity.imageFileName as String)

                        // set image attributes
                        productEntity.image = imageResult.data.toString()
                        productEntity.imageFileName = file.name

                        // update data
                        executeUpdate(productEntity)
                    }
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

}