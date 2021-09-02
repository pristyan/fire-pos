package com.fire.pos.domain.historydetail

import com.fire.pos.data.repository.transaction.TransactionRepository
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Transaction
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HistoryDetailInteractorImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
): HistoryDetailInteractor {

    override suspend fun getTransactionDetail(id: String): Result<Transaction> {
        return when (val result = transactionRepository.getTransactionDetail(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(Transaction(result.data))
        }
    }

}