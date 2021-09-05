package com.fire.pos.domain.transactiondetail

import com.fire.pos.data.repository.transaction.TransactionRepository
import com.fire.core.model.Result
import com.fire.pos.model.view.Transaction
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionDetailInteractorImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
): TransactionDetailInteractor {

    override suspend fun getTransactionDetail(id: String): Result<Transaction> {
        return when (val result = transactionRepository.getTransactionDetail(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(Transaction(result.data))
        }
    }

}