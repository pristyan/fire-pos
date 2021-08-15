package com.fire.pos.domain.repository.remote

import android.net.Uri
import com.fire.pos.data.response.BaseResponse
import com.fire.pos.data.response.Result
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.io.File


/**
 * Created by Chandra.
 **/

interface ProductRemoteDataSource {

    suspend fun getProductList(): BaseResponse<QuerySnapshot>

    suspend fun uploadImage(file: File): BaseResponse<Uri>

    suspend fun deleteImage(fileName: String): BaseResponse<Void>

    suspend fun addProduct(
        name: String,
        price: Long,
        stock: Long,
        image: String,
        imageFileName: String
    ): BaseResponse<DocumentReference>

    suspend fun updateProduct(
        id: String,
        name: String,
        price: Long,
        stock: Long,
        image: String,
        imageFileName: String
    ): BaseResponse<Void>

    suspend fun deleteProduct(id: String): BaseResponse<Void>

}