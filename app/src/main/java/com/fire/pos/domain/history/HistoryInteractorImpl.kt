package com.fire.pos.domain.history

import com.fire.pos.data.repository.transaction.TransactionRepository
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Transaction
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HistoryInteractorImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
): HistoryInteractor {

    override suspend fun getTransactionList(): Result<List<Transaction>> {
        return when (val result = transactionRepository.getTransactionList()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.map { Transaction(it) } ?: emptyList()
                Result.Success(list)
            }
        }
    }

}