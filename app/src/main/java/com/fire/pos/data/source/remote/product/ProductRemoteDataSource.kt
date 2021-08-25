package com.fire.pos.data.source.remote.product

import android.net.Uri
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.model.response.BaseResponse
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.io.File


/**
 * Created by Chandra.
 **/

interface ProductRemoteDataSource {

    suspend fun getProductList(): Result<QuerySnapshot>

    suspend fun uploadImage(file: File): Result<Uri>

    suspend fun deleteImage(fileName: String): Result<Void>

    suspend fun addProduct(product: ProductEntity): Result<DocumentReference>

    suspend fun updateProduct(product: ProductEntity): Result<Void>

    suspend fun deleteProduct(id: String): Result<Void>

}