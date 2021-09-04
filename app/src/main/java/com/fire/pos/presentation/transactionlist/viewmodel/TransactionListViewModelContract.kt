package com.fire.pos.presentation.transactionlist.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.Transaction
import kotlinx.coroutines.Job
import java.util.*


/**
 * Created by Chandra.
 **/

interface TransactionListViewModelContract : BaseViewModelContract {

    val historySuccess: LiveData<List<Transaction>>

    val errorMessage: LiveData<String>

    fun getTransactionList(startDate: Date, endDate: Date): Job

}