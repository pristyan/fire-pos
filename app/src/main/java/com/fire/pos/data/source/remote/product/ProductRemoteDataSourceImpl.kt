package com.fire.pos.data.source.remote.product

import android.net.Uri
import com.fire.pos.constant.FirestoreConstant
import com.fire.pos.constant.StorageConstant
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.util.await
import com.fire.pos.util.getException
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

class ProductRemoteDataSourceImpl @Inject constructor(
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

    override suspend fun getProductList(): Result<QuerySnapshot> {
        val task = firebaseFirestore
            .collection(FirestoreConstant.COLLECTION_USERS)
            .document(firebaseAuth.uid as String)
            .collection(FirestoreConstant.COLLECTION_PRODUCTS)
            .get()
        return task.await()
    }

    override suspend fun uploadImage(file: File): Result<Uri> {
        val imageReference = userImageReference.child(file.name)
        val task = imageReference.putFile(Uri.fromFile(file))
        val response = task.await()
        return if (response.isSuccess) {
            val taskUrl = imageReference.downloadUrl
            taskUrl.await()
        } else {
            Result.failure(response.getException())
        }
    }

    override suspend fun deleteImage(fileName: String): Result<Void> {
        val imageReference = userImageReference.child(fileName)
        val task = imageReference.delete()
        return task.await()
    }

    override suspend fun addProduct(product: ProductEntity): Result<DocumentReference> {
        val data = mapOf<String, Any>(
            FirestoreConstant.FIELD_NAME to product.name as String,
            FirestoreConstant.FIELD_PRICE to (product.price ?: 0L),
            FirestoreConstant.FIELD_STOCK to (product.stock ?: 0L),
            FirestoreConstant.FIELD_IMAGE to product.image as String,
            FirestoreConstant.FIELD_IMAGE_FILE_NAME to product.imageFileName as String
        )
        val task = productCollection.add(data)
        return task.await()
    }

    override suspend fun updateProduct(product: ProductEntity): Result<Void> {
        val data = mapOf<String, Any>(
            FirestoreConstant.FIELD_NAME to product.name as String,
            FirestoreConstant.FIELD_PRICE to (product.price ?: 0L),
            FirestoreConstant.FIELD_STOCK to (product.stock ?: 0L),
            FirestoreConstant.FIELD_IMAGE to product.image as String,
            FirestoreConstant.FIELD_IMAGE_FILE_NAME to product.imageFileName as String
        )
        val task = productCollection.document(product.id as String).set(data)
        return task.await()
    }

    override suspend fun deleteProduct(id: String): Result<Void> {
        val task = productCollection.document(id).delete()
        return task.await()
    }
}