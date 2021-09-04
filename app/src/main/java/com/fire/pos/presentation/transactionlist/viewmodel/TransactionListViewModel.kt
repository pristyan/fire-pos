package com.fire.pos.presentation.transactionlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.pos.base.viewmodel.BaseViewModel
import com.fire.pos.domain.transactionlist.TransactionListInteractor
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Transaction
import com.fire.pos.scheduler.SchedulerProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val transactionListInteractor: TransactionListInteractor
) : BaseViewModel(), TransactionListViewModelContract {

    private val _historySuccess = MutableLiveData<List<Transaction>>()
    override val historySuccess: LiveData<List<Transaction>>
        get() = _historySuccess

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override fun getTransactionList(
        startDate: Date,
        endDate: Date
    ): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            transactionListInteractor.getTransactionList(startDate, endDate)
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _historySuccess.value = result.data
        }

        setLoading(false)
    }


}