package com.fire.pos.presentation.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.core.model.Result
import com.fire.pos.model.view.User
import com.fire.pos.domain.login.LoginInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class LoginViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val loginInteractor: LoginInteractor
) : BaseViewModel(), LoginViewModelContract {

    private val _loginSuccess = MutableLiveData<User>()
    override val loginSuccess: LiveData<User> = _loginSuccess

    private val _loginError = MutableLiveData<String>()
    override val loginError: LiveData<String> = _loginError

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    override val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    override fun checkLoggedInUser(): Job = launch(schedulerProvider.ui()) {
        val result = withContext(schedulerProvider.io()) {
            loginInteractor.isLoggedIn()
        }

        _isUserLoggedIn.value = result
    }

    override fun login(email: String, password: String): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            loginInteractor.login(email, password)
        }

        when (result) {
            is Result.Error -> _loginError.value = result.message
            is Result.Success -> _loginSuccess.value = result.data
        }

        setLoading(false)
    }

}