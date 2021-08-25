package com.fire.pos.domain.productdetail

import com.fire.pos.model.view.Product
import com.fire.pos.model.response.Result
import java.io.File

/**
 * Created by Chandra.
 **/

interface ProductDetailInteractor {

    suspend fun addProduct(name: String, price: Long, stock: Long, image: File): Result<Product>

    suspend fun updateProduct(product: Product, newImage: File?, ): Result<Product>

    suspend fun deleteProduct(product: Product): Result<Boolean>

}