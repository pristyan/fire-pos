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

    suspend fun getProductWithCart(): Result<List<ProductCartEntity>>

    suspend fun getProductId(id: String): Result<ProductEntity>

    suspend fun addProduct(productEntity: ProductEntity, file: File): Result<ProductEntity>

    suspend fun updateProduct(productEntity: ProductEntity, file: File?): Result<ProductEntity>

    suspend fun deleteProduct(id: String): Result<Boolean>

    suspend fun addProductToCart(productCartEntity: ProductCartEntity): Result<ProductCartEntity>

    suspend fun updateCart(productCartEntity: ProductCartEntity): Result<ProductCartEntity>

    suspend fun deleteProductFromCart(productCartEntity: ProductCartEntity): Result<Boolean>

    suspend fun clearCart(): Result<Boolean>

    suspend fun getCart(): Result<List<ProductCartEntity>>

}