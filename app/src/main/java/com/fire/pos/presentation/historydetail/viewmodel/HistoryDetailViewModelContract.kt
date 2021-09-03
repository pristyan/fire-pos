package com.fire.pos.presentation.historydetail.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.Transaction
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface HistoryDetailViewModelContract : BaseViewModelContract {

    val detailSuccess: LiveData<Transaction>

    val errorMessage: LiveData<String>

    fun getTransactionDetail(id: String): Job

}