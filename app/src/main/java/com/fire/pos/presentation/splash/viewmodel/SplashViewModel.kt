package com.fire.pos.presentation.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.pos.domain.splash.SplashInteractor
import com.fire.core.model.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class SplashViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val splashInteractor: SplashInteractor
): BaseViewModel(), SplashViewModelContract {

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    override val isUserLoggedIn: LiveData<Boolean>
        get() = _isUserLoggedIn

    override fun checkUser(): Job = launch(schedulerProvider.ui()) {
        delay(1500)
        val result = withContext(schedulerProvider.io()) {
            splashInteractor.isLoggedIn()
        }

        when (result) {
            is Result.Error -> _isUserLoggedIn.value = false
            is Result.Success -> _isUserLoggedIn.value = result.data ?: false
        }
    }
}