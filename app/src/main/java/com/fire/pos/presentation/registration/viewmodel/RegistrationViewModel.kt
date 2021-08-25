package com.fire.pos.presentation.registration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.pos.base.viewmodel.BaseViewModel
import com.fire.pos.model.response.Result
import com.fire.pos.domain.registration.RegistrationInteractor
import com.fire.pos.scheduler.SchedulerProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class RegistrationViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val registrationInteractor: RegistrationInteractor
) : BaseViewModel(), RegistrationViewModelContract {

    private val _isRegistrationSuccess = MutableLiveData<Boolean>()
    override val isRegistrationSuccess: LiveData<Boolean> = _isRegistrationSuccess

    private val _isRegistrationError = MutableLiveData<String>()
    override val isRegistrationError: LiveData<String> = _isRegistrationError

    override fun registration(email: String, password: String, storeName: String) = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            registrationInteractor.registerUser(email, password, storeName)
        }

        when (result) {
            is Result.Error -> _isRegistrationError.value = result.message
            is Result.Success -> _isRegistrationSuccess.value = true
        }

        setLoading(false)
    }

}