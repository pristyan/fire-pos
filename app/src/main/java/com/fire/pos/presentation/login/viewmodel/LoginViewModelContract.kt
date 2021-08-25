package com.fire.pos.presentation.login.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.User
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface LoginViewModelContract: BaseViewModelContract {

    val loginSuccess: LiveData<User>

    val loginError: LiveData<String>

    val isUserLoggedIn: LiveData<Boolean>

    fun checkLoggedInUser(): Job

    fun login(email: String, password: String): Job

}