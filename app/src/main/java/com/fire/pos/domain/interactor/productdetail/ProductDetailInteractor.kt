package com.fire.pos.domain.interactor.productdetail

import com.fire.pos.data.view.Product
import com.fire.pos.data.response.Result
import java.io.File

/**
 * Created by Chandra.
 **/

interface ProductDetailInteractor {

    suspend fun addProduct(
        name: String,
        price: Long,
        stock: Long,
        image: File
    ): Result<Product>

    suspend fun updateProduct(
        product: Product,
        newImage: File?,
    ): Result<Product>

    suspend fun deleteProduct(id: String): Result<Boolean>

}