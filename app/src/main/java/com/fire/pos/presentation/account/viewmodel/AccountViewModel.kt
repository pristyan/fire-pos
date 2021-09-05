package com.fire.pos.presentation.account.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.pos.domain.account.AccountInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val accountInteractor: AccountInteractor
): BaseViewModel(), AccountViewModelContract {

    private val _logoutSuccess = MutableLiveData<Boolean>()
    override val logoutSuccess: LiveData<Boolean> = _logoutSuccess

    override fun logout(): Job = launch(schedulerProvider.ui()) {
        setLoading(true)
        withContext(schedulerProvider.io()) { accountInteractor.logout() }
        setLoading(false)
        _logoutSuccess.value = true
    }
}