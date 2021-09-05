package com.fire.pos.presentation.transactiondetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.pos.domain.transactiondetail.TransactionDetailInteractor
import com.fire.core.model.Result
import com.fire.pos.model.view.Transaction
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionDetailViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val transactionDetailInteractor: TransactionDetailInteractor
): BaseViewModel(), TransactionDetailViewModelContract {
    private val _detailSuccess = MutableLiveData<Transaction>()
    override val detailSuccess: LiveData<Transaction>
        get() = _detailSuccess

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override fun getTransactionDetail(id: String): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            transactionDetailInteractor.getTransactionDetail(id)
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _detailSuccess.value = result.data
        }

        setLoading(false)
    }
}