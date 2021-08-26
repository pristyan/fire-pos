package com.fire.pos.data.repository.product

import com.fire.pos.data.source.local.product.ProductLocalDataSource
import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.BaseResponse
import com.fire.pos.util.getException
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

    override suspend fun getProductList(): BaseResponse<List<ProductEntity>> {
        return if (productLocalDataSource.needFetchRemote()) {
            val result = productRemoteDataSource.getProductList()
            if (result.isSuccess) {

                val list = result.getOrNull()?.documents?.map { doc ->
                    ProductEntity(doc.id, doc)
                } ?: emptyList()

                productLocalDataSource.setLastFetch(System.currentTimeMillis())
                productLocalDataSource.insertProducts(list)
                BaseResponse(list)

            } else {
                BaseResponse(result.getException())
            }

        } else {
            val result = productLocalDataSource.getProductList()
            if (result.isSuccess) {
                val list = result.getOrNull()?.map { ProductEntity(it) } ?: emptyList()
                BaseResponse(list)
            } else {
                BaseResponse(result.getException())
            }
        }
    }

    override suspend fun getProductId(id: String): BaseResponse<ProductEntity> {
        val result = productLocalDataSource.getProductById(id)
        return if (result.isSuccess) {
            BaseResponse(ProductEntity(result.getOrNull()))
        } else {
            BaseResponse(result.getException())
        }
    }

    override suspend fun addProduct(
        productEntity: ProductEntity,
        file: File
    ): BaseResponse<ProductEntity> {

        // upload image
        val imageResult = productRemoteDataSource.uploadImage(file)
        return if (imageResult.isSuccess) {

            // set image attributes
            productEntity.image = imageResult.getOrNull()?.toString()
            productEntity.imageFileName = file.name

            // add to fire store
            val remoteResult = productRemoteDataSource.addProduct(productEntity)
            if (remoteResult.isSuccess) {

                // set product id from fire store
                val docRefs = remoteResult.getOrNull() as DocumentReference
                productEntity.id = docRefs.id

                // add to room
                val localResult = productLocalDataSource.insertProduct(productEntity)
                if (localResult.isSuccess) {
                    BaseResponse(productEntity)
                } else {
                    BaseResponse(localResult.getException())
                }

            } else {
                BaseResponse(remoteResult.getException())
            }

        } else {
            BaseResponse(imageResult.getException())
        }


    }

    override suspend fun updateProduct(
        productEntity: ProductEntity,
        file: File?
    ): BaseResponse<ProductEntity> {

        suspend fun update(entity: ProductEntity): BaseResponse<ProductEntity> {
            // update fire store
            val remoteResult = productRemoteDataSource.updateProduct(entity)
            return if (remoteResult.isSuccess) {

                // update room
                val localResult = productLocalDataSource.updateProduct(entity)
                if (localResult.isSuccess) {
                    BaseResponse(entity)
                } else {
                    BaseResponse(localResult.getException())
                }

            } else {
                BaseResponse(remoteResult.getException())
            }
        }

        return if (file == null) {
            update(productEntity)
        } else {

            // upload image
            val imageResult = productRemoteDataSource.uploadImage(file)
            return if (imageResult.isSuccess) {

                // delete previous image
                productRemoteDataSource.deleteImage(productEntity.imageFileName as String)

                // set image attributes
                productEntity.image = imageResult.getOrNull().toString()
                productEntity.imageFileName = file.name

                // update data
                update(productEntity)
            } else {
                BaseResponse(imageResult.getException())
            }
        }
    }

    override suspend fun deleteProduct(id: String): BaseResponse<Boolean> {
        // delete from fire store
        val remoteResult = productRemoteDataSource.deleteProduct(id)
        return if (remoteResult.isSuccess) {

            // delete from room
            val localResult = productLocalDataSource.deleteProduct(id)
            if (localResult.isSuccess) {
                BaseResponse(localResult.getOrNull() ?: false)
            } else {
                BaseResponse(localResult.getException())
            }

        } else {
            BaseResponse(remoteResult.getException())
        }
    }

}