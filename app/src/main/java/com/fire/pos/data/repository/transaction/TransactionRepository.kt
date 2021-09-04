package com.fire.pos.data.repository.transaction

import com.fire.pos.model.entity.TransactionEntity
import com.fire.pos.model.response.Result
import java.util.*


/**
 * Created by Chandra.
 **/

interface TransactionRepository {

    suspend fun createTransaction(entity: TransactionEntity): Result<Boolean>

    suspend fun getTransactionList(
        startDate: Date,
        endDate: Date
    ): Result<List<TransactionEntity>>

    suspend fun getTransactionDetail(id: String): Result<TransactionEntity>
}