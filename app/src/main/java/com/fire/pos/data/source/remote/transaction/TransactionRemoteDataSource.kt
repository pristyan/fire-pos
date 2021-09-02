package com.fire.pos.data.source.remote.transaction

import com.fire.pos.model.entity.TransactionEntity
import com.fire.pos.model.response.Result
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot


/**
 * Created by Chandra.
 **/

interface TransactionRemoteDataSource {

    suspend fun createTransaction(entity: TransactionEntity): Result<Void>

    suspend fun insertTransactionItems(entity: TransactionEntity): Result<Void>

    suspend fun getTransactionList(): Result<QuerySnapshot>

    suspend fun getTransactionItems(id: String): Result<QuerySnapshot>

    suspend fun getTransactionDetail(id: String): Result<DocumentSnapshot>
}