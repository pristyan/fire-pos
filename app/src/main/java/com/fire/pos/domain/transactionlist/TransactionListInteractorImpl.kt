package com.fire.pos.domain.transactionlist

import com.fire.pos.data.repository.transaction.TransactionRepository
import com.fire.core.model.Result
import com.fire.pos.model.view.Transaction
import java.util.*
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionListInteractorImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
) : TransactionListInteractor {

    override suspend fun getTransactionList(
        startDate: Date,
        endDate: Date
    ): Result<List<Transaction>> {
        return when (val result = transactionRepository.getTransactionList(startDate, endDate)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.map { Transaction(it) } ?: emptyList()
                Result.Success(list)
            }
        }
    }

}