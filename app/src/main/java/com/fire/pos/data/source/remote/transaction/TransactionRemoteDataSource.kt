package com.fire.pos.data.source.remote.transaction

import com.fire.pos.model.entity.TransactionEntity
import com.fire.core.model.Result
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.*


/**
 * Created by Chandra.
 **/

interface TransactionRemoteDataSource {

    suspend fun createTransaction(entity: TransactionEntity): Result<Void>

    suspend fun insertTransactionItems(entity: TransactionEntity): Result<Void>

    suspend fun getTransactionList(startDate: Date, endDate: Date): Result<QuerySnapshot>

    suspend fun getTransactionItems(id: String): Result<QuerySnapshot>

    suspend fun getTransactionDetail(id: String): Result<DocumentSnapshot>
}