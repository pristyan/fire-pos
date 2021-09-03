package com.fire.pos.data.repository.product

import com.fire.pos.model.entity.ProductCartEntity
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.Result
import java.io.File


/**
 * Created by Chandra.
 **/

interface ProductRepository {

    suspend fun getProductList(): Result<List<ProductEntity>>

    suspend fun getProductId(id: String): Result<ProductEntity>

    suspend fun addProduct(productEntity: ProductEntity, file: File): Result<ProductEntity>

    suspend fun updateProduct(productEntity: ProductEntity, file: File?): Result<ProductEntity>

    suspend fun updateProductStock(items: List<ProductCartEntity>): Result<Boolean>

    suspend fun deleteProduct(id: String): Result<Boolean>

}