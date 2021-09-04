package com.fire.pos.data.source.remote.transaction

import com.fire.pos.constant.FirestoreConstant
import com.fire.pos.model.entity.TransactionEntity
import com.fire.pos.model.response.Result
import com.fire.pos.util.await
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.util.*
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : TransactionRemoteDataSource {

    private val transactionCollection: CollectionReference
        get() = firebaseFirestore
            .collection(FirestoreConstant.COLLECTION_USERS)
            .document(firebaseAuth.uid as String)
            .collection(FirestoreConstant.COLLECTION_TRANSACTIONS)

    override suspend fun createTransaction(entity: TransactionEntity): Result<Void> {
        val parameter = mapOf<String, Any>(
            FirestoreConstant.FIELD_TRANSACTION_ID to entity.id.orEmpty(),
            FirestoreConstant.FIELD_PAYMENT_METHOD to entity.paymentMethod.orEmpty(),
            FirestoreConstant.FIELD_TOTAL to entity.totalPrice,
            FirestoreConstant.FIELD_CREATED_AT to (entity.createdAt ?: Timestamp(Date()))
        )
        val task = transactionCollection.document(entity.id.orEmpty()).set(parameter)
        return task.await()
    }

    override suspend fun insertTransactionItems(entity: TransactionEntity): Result<Void> {
        val batch = firebaseFirestore.batch()

        entity.items?.forEach {
            val collection = transactionCollection
                .document(entity.id.orEmpty())
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

    override suspend fun getTransactionList(
        startDate: Date,
        endDate: Date
    ): Result<QuerySnapshot> {
        val task = transactionCollection
            .whereGreaterThanOrEqualTo(FirestoreConstant.FIELD_CREATED_AT, startDate)
            .whereLessThanOrEqualTo(FirestoreConstant.FIELD_CREATED_AT, endDate)
            .orderBy(FirestoreConstant.FIELD_CREATED_AT, Query.Direction.DESCENDING)
            .get()
        return task.await()
    }

    override suspend fun getTransactionDetail(id: String): Result<DocumentSnapshot> {
        val task = transactionCollection.document(id).get()
        return task.await()
    }

    override suspend fun getTransactionItems(id: String): Result<QuerySnapshot> {
        val task = transactionCollection
            .document(id)
            .collection(FirestoreConstant.COLLECTION_TRANSACTION_ITEMS)
            .get()
        return task.await()
    }
}