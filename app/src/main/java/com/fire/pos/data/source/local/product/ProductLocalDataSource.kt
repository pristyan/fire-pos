package com.fire.pos.data.source.local.product

import com.fire.pos.model.db.ProductDbEntity
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.Result


/**
 * Created by Chandra.
 **/

interface ProductLocalDataSource {

    fun setLastFetch(data: Long)

    fun needFetchRemote(): Boolean

    suspend fun getProductList(): Result<List<ProductDbEntity>>

    suspend fun getProductById(id: String): Result<ProductDbEntity>

    suspend fun insertProducts(list: List<ProductEntity>): Result<Boolean>

    suspend fun insertProduct(productEntity: ProductEntity): Result<Boolean>

    suspend fun updateProduct(productEntity: ProductEntity): Result<Boolean>

    suspend fun deleteProduct(id: String): Result<Boolean>

}