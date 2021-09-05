package com.fire.pos.domain.transactiondetail

import com.fire.core.model.Result
import com.fire.pos.model.view.Transaction


/**
 * Created by Chandra.
 **/

interface TransactionDetailInteractor {

    suspend fun getTransactionDetail(id: String): Result<Transaction>

}