package com.fire.pos.presentation.registration.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface RegistrationViewModelContract : BaseViewModelContract {

    val isRegistrationSuccess: LiveData<Boolean>
    val isRegistrationError: LiveData<String>

    fun registration(email: String, password: String, storeName: String): Job

}