package com.fire.pos.presentation.historydetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.pos.base.viewmodel.BaseViewModel
import com.fire.pos.domain.historydetail.HistoryDetailInteractor
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Transaction
import com.fire.pos.scheduler.SchedulerProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HistoryDetailViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val historyDetailInteractor: HistoryDetailInteractor
): BaseViewModel(), HistoryDetailViewModelContract {
    private val _detailSuccess = MutableLiveData<Transaction>()
    override val detailSuccess: LiveData<Transaction>
        get() = _detailSuccess

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override fun getTransactionDetail(id: String): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            historyDetailInteractor.getTransactionDetail(id)
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _detailSuccess.value = result.data
        }

        setLoading(false)
    }
}