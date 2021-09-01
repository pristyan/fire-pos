package com.fire.pos.data.source.remote.transaction

import com.fire.pos.constant.FirestoreConstant
import com.fire.pos.model.entity.TransactionEntity
import com.fire.pos.model.response.Result
import com.fire.pos.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): TransactionRemoteDataSource {

    private val transactionCollection: CollectionReference
        get() = firebaseFirestore
            .collection(FirestoreConstant.COLLECTION_USERS)
            .document(firebaseAuth.uid as String)
            .collection(FirestoreConstant.COLLECTION_TRANSACTIONS)

    override suspend fun createTransaction(entity: TransactionEntity): Result<DocumentReference> {
        val parameter = mapOf<String, Any>(
            FirestoreConstant.FIELD_PAYMENT_METHOD to entity.paymentMethod.orEmpty(),
            FirestoreConstant.FIELD_TOTAL to entity.totalPrice,
            FirestoreConstant.FIELD_CREATED_AT to entity.createdAt.orEmpty()
        )
        val task = transactionCollection.add(parameter)
        return task.await()
    }

    override suspend fun insertTransactionItems(
        id: String,
        entity: TransactionEntity
    ): Result<Void> {
        val batch = firebaseFirestore.batch()

        entity.items?.forEach {
            val collection = transactionCollection
                .document(id)
                .collection(FirestoreConstant.COLLECTION_TRANSACTION_ITEMS)

            val parameter = mapOf<String, Any>(
                FirestoreConstant.FIELD_CART_ID to it.id,
                FirestoreConstant.FIELD_CART_PRODUCT_ID to it.productId,
                FirestoreConstant.FIELD_CART_PRODUCT_NAME to it.productName,
                FirestoreConstant.FIELD_CART_PRODUCT_IMAGE to it.productImage,
                FirestoreConstant.FIELD_CART_PRODUCT_PRICE to it.productPrice,
                FirestoreConstant.FIELD_CART_QTY to it.qty
            )

            batch.set(collection.document(), parameter)
        }

        return batch.commit().await()
    }

}