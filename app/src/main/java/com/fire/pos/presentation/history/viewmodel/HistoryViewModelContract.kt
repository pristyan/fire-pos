package com.fire.pos.presentation.history.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.Transaction
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface HistoryViewModelContract : BaseViewModelContract {

    val historySuccess: LiveData<List<Transaction>>

    val errorMessage: LiveData<String>

    fun getTransactionList(): Job

}