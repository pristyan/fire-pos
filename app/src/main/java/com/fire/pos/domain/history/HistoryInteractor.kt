package com.fire.pos.domain.history

import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Transaction
import java.util.*


/**
 * Created by Chandra.
 **/

interface HistoryInteractor {

    suspend fun getTransactionList(startDate: Date, endDate: Date): Result<List<Transaction>>

}