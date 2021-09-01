package com.fire.pos.data.repository.transaction

import com.fire.pos.data.source.remote.transaction.TransactionRemoteDataSource
import com.fire.pos.model.entity.TransactionEntity
import com.fire.pos.model.response.Result
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionRepositoryImpl @Inject constructor(
    private val transactionRemoteDataSource: TransactionRemoteDataSource
): TransactionRepository {

    override suspend fun createTransaction(entity: TransactionEntity): Result<Boolean> {
        // create transaction document
        return when (val result = transactionRemoteDataSource.createTransaction(entity)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                // insert transaction items
                val docId = result.data?.id.orEmpty()
                when (val iResult = transactionRemoteDataSource.insertTransactionItems(docId, entity)) {
                    is Result.Error -> Result.Error(iResult.message)
                    is Result.Success -> Result.Success(true)
                }
            }
        }
    }

}