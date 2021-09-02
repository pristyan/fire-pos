package com.fire.pos.domain.historydetail

import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Transaction


/**
 * Created by Chandra.
 **/

interface HistoryDetailInteractor {

    suspend fun getTransactionDetail(id: String): Result<Transaction>

}