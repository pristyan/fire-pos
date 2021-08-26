package com.fire.pos.data.repository.product

import com.fire.pos.data.source.local.product.ProductLocalDataSource
import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.Result
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

        // check last fetch time
        return if (productLocalDataSource.needFetchRemote()) {

            // fetch from fire store
            when (val result = productRemoteDataSource.getProductList()) {
                is Result.Error -> Result.Error(result.message)
                is Result.Success -> {

                    val list = result.data?.documents?.map { doc ->
                        ProductEntity(doc.id, doc)
                    } ?: emptyList()

                    // set last fetch time
                    productLocalDataSource.setLastFetch(System.currentTimeMillis())

                    // insert into room
                    productLocalDataSource.insertProducts(list)

                    Result.Success(list)
                }
            }
        } else {

            // fetch from room
            when (val result = productLocalDataSource.getProductList()) {
                is Result.Error -> Result.Error(result.message)
                is Result.Success -> Result.Success(
                    result.data?.map { ProductEntity(it) } ?: emptyList()
                )
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
                        val docRefs = remoteResult.data
                        productEntity.id = docRefs?.id

                        // add to room
                        when (val result = productLocalDataSource.insertProduct(productEntity)) {
                            is Result.Error -> Result.Error(result.message)
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
                    is Result.Success -> Result.Success(localResult.data ?: false)
                }
            }
        }
    }

}