package com.fire.pos.presentation.history.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.pos.base.viewmodel.BaseViewModel
import com.fire.pos.domain.history.HistoryInteractor
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

class HistoryViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val historyInteractor: HistoryInteractor
) : BaseViewModel(), HistoryViewModelContract {

    private val _historySuccess = MutableLiveData<List<Transaction>>()
    override val historySuccess: LiveData<List<Transaction>>
        get() = _historySuccess

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override fun getTransactionList(): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            historyInteractor.getTransactionList()
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _historySuccess.value = result.data
        }

        setLoading(false)
    }


}