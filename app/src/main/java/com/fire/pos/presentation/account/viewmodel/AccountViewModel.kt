package com.fire.pos.presentation.account.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.pos.base.viewmodel.BaseViewModel
import com.fire.pos.data.source.remote.account.AccountRemoteDataSource
import com.fire.pos.scheduler.SchedulerProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val accountRemoteDataSource: AccountRemoteDataSource
): BaseViewModel(), AccountViewModelContract {

    private val _logoutSuccess = MutableLiveData<Boolean>()
    override val logoutSuccess: LiveData<Boolean> = _logoutSuccess

    override fun logout(): Job = launch(schedulerProvider.ui()) {
        accountRemoteDataSource.logout()
        _logoutSuccess.value = true
    }
}