package com.fire.pos.data.source.remote.transaction

import com.fire.pos.model.entity.TransactionEntity
import com.fire.pos.model.response.Result
import com.google.firebase.firestore.DocumentReference


/**
 * Created by Chandra.
 **/

interface TransactionRemoteDataSource {

    suspend fun createTransaction(entity: TransactionEntity): Result<DocumentReference>

    suspend fun insertTransactionItems(
        id: String,
        entity: TransactionEntity
    ): Result<Void>

}