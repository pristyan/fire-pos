package com.fire.pos.data.repository.product

import com.fire.pos.data.source.local.product.ProductLocalDataSource
import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.Result
import com.google.firebase.firestore.DocumentReference
import java.io.File
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductRepositoryImpl @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getProductList(): Result<List<ProductEntity>> {
        return if (productLocalDataSource.needFetchRemote()) {
            when (val result = productRemoteDataSource.getProductList()) {
                is Result.Error -> Result.Error(result.message)
                is Result.Success -> {
                    val list = result.data?.documents?.map { doc ->
                        ProductEntity(doc.id, doc)
                    } ?: emptyList()

                    productLocalDataSource.setLastFetch(System.currentTimeMillis())
                    productLocalDataSource.insertProducts(list)
                    Result.Success(list)
                }
            }
        } else {
            when (val result = productLocalDataSource.getProductList()) {
                is Result.Error -> Result.Error(result.message)
                is Result.Success -> {
                    val list = result.data?.map { ProductEntity(it) } ?: emptyList()
                    Result.Success(list)
                }
            }
        }
    }

    override suspend fun getProductId(id: String): Result<ProductEntity> {
        return when (val result = productLocalDataSource.getProductById(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(ProductEntity(result.data))
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
                        val docRefs = remoteResult.data as DocumentReference
                        productEntity.id = docRefs.id

                        // add to room
                        when (
                            val localResult = productLocalDataSource.insertProduct(productEntity)
                        ) {
                            is Result.Error -> Result.Error(localResult.message)
                            is Result.Success -> Result.Success(productEntity)
                        }
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
            // update fire store
            return when (val remoteResult = productRemoteDataSource.updateProduct(entity)) {
                is Result.Error -> Result.Error(remoteResult.message)
                is Result.Success -> {
                    // update room
                    when (val localResult = productLocalDataSource.updateProduct(entity)) {
                        is Result.Error -> Result.Error(localResult.message)
                        is Result.Success -> Result.Success(entity)
                    }
                }
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

    override suspend fun deleteProduct(id: String): Result<Boolean> {
        // delete from fire store
        return when (val result = productRemoteDataSource.deleteProduct(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                // delete from room
                when (val localResult = productLocalDataSource.deleteProduct(id)) {
                    is Result.Error -> Result.Error(localResult.message)
                    is Result.Success -> {
                        Result.Success(localResult.data ?: false)
                    }
                }
            }
        }
    }

}