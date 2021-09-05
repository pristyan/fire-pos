package com.fire.pos.domain.transactionlist

import com.fire.core.model.Result
import com.fire.pos.model.view.Transaction
import java.util.*


/**
 * Created by Chandra.
 **/

interface TransactionListInteractor {

    suspend fun getTransactionList(startDate: Date, endDate: Date): Result<List<Transaction>>

}