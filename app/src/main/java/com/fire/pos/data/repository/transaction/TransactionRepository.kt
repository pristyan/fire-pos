package com.fire.pos.data.repository.transaction

import com.fire.pos.model.entity.TransactionEntity
import com.fire.pos.model.response.Result


/**
 * Created by Chandra.
 **/

interface TransactionRepository {

    suspend fun createTransaction(entity: TransactionEntity): Result<Boolean>

}