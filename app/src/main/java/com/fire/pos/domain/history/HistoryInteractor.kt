package com.fire.pos.domain.history

import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Transaction


/**
 * Created by Chandra.
 **/

interface HistoryInteractor {

    suspend fun getTransactionList(): Result<List<Transaction>>

}