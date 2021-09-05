package com.fire.pos.presentation.transactiondetail.viewmodel

import androidx.lifecycle.LiveData
import com.fire.core.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.Transaction
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface TransactionDetailViewModelContract : BaseViewModelContract {

    val detailSuccess: LiveData<Transaction>

    val errorMessage: LiveData<String>

    fun getTransactionDetail(id: String): Job

}