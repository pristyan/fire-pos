package com.fire.pos.domain.repository.remote

import android.net.Uri
import com.fire.pos.data.constant.FirestoreConstant
import com.fire.pos.data.constant.StorageConstant
import com.fire.pos.data.response.BaseResponse
import com.fire.pos.data.response.Result
import com.fire.pos.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductRemoteRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : ProductRemoteDataSource {

    private val productCollection: CollectionReference
        get() = firebaseFirestore
            .collection(FirestoreConstant.COLLECTION_USERS)
            .document(firebaseAuth.uid as String)
            .collection(FirestoreConstant.COLLECTION_PRODUCTS)

    private val userImageReference
        get() = firebaseStorage.reference
            .child(firebaseAuth.uid as String)
            .child(StorageConstant.REFERENCE_IMAGES)
            .child(StorageConstant.REFERENCE_PRODUCTS)

    override suspend fun getProductList(): BaseResponse<QuerySnapshot> {
        val task = firebaseFirestore
            .collection(FirestoreConstant.COLLECTION_USERS)
            .document(firebaseAuth.uid as String)
            .collection(FirestoreConstant.COLLECTION_PRODUCTS)
            .get()
        return task.await()
    }

    override suspend fun uploadImage(file: File): BaseResponse<Uri> {
        val imageReference = userImageReference.child(file.name)
        val task = imageReference.putFile(Uri.fromFile(file))
        val response = task.await()
        return if (response.isSuccess) {
            val taskUrl = imageReference.downloadUrl
            taskUrl.await()
        } else {
            BaseResponse(data = null, throwable = response.throwable)
        }
    }

    override suspend fun deleteImage(fileName: String): BaseResponse<Void> {
        val imageReference = userImageReference.child(fileName)
        val task = imageReference.delete()
        return task.await()
    }

    override suspend fun addProduct(
        name: String,
        price: Long,
        stock: Long,
        image: String,
        imageFileName: String
    ): BaseResponse<DocumentReference> {
        val data = mapOf<String, Any>(
            FirestoreConstant.FIELD_NAME to name,
            FirestoreConstant.FIELD_PRICE to price,
            FirestoreConstant.FIELD_STOCK to stock,
            FirestoreConstant.FIELD_IMAGE to image,
            FirestoreConstant.FIELD_IMAGE_FILE_NAME to imageFileName
        )
        val task = productCollection.add(data)
        return task.await()
    }

    override suspend fun updateProduct(
        id: String,
        name: String,
        price: Long,
        stock: Long,
        image: String,
        imageFileName: String
    ): BaseResponse<Void> {
        val data = mapOf<String, Any>(
            FirestoreConstant.FIELD_NAME to name,
            FirestoreConstant.FIELD_PRICE to price,
            FirestoreConstant.FIELD_STOCK to stock,
            FirestoreConstant.FIELD_IMAGE to image,
            FirestoreConstant.FIELD_IMAGE_FILE_NAME to imageFileName
        )
        println("ID : $id")
        println(data)

        val task = productCollection.document(id).set(data)
        return task.await()
    }

    override suspend fun deleteProduct(id: String): BaseResponse<Void> {
        val task = productCollection.document(id).delete()
        return task.await()
    }
}