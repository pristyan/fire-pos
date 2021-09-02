package com.fire.pos.data.repository.transaction

import com.fire.pos.data.source.remote.transaction.TransactionRemoteDataSource
import com.fire.pos.model.entity.ProductCartEntity
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
                when (val iResult = transactionRemoteDataSource.insertTransactionItems(entity)) {
                    is Result.Error -> Result.Error(iResult.message)
                    is Result.Success -> Result.Success(true)
                }
            }
        }
    }

    override suspend fun getTransactionList(): Result<List<TransactionEntity>> {
        return when (val result = transactionRemoteDataSource.getTransactionList()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.documents?.map { TransactionEntity(it) }
                Result.Success(list)
            }
        }
    }

    override suspend fun getTransactionDetail(id: String): Result<TransactionEntity> {
        // get transaction detail
        return when (val result = transactionRemoteDataSource.getTransactionDetail(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {

               // get transaction products
                when (val iResult = transactionRemoteDataSource.getTransactionItems(id)) {
                    is Result.Error -> Result.Error(iResult.message)
                    is Result.Success -> {
                        val list = iResult.data?.documents?.map {
                            ProductCartEntity(it)
                        } ?: emptyList()
                        val transaction = TransactionEntity(result.data, list)
                        Result.Success(transaction)
                    }
                }
            }
        }
    }

}