package com.fire.pos.data.repository.product

import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.BaseResponse
import java.io.File


/**
 * Created by Chandra.
 **/

interface ProductRepository {

    suspend fun getProductList(): BaseResponse<List<ProductEntity>>

    suspend fun getProductId(id: String): BaseResponse<ProductEntity>

    suspend fun addProduct(productEntity: ProductEntity, file: File): BaseResponse<ProductEntity>

    suspend fun updateProduct(productEntity: ProductEntity, file: File?): BaseResponse<ProductEntity>

    suspend fun deleteProduct(id: String): BaseResponse<Boolean>

}