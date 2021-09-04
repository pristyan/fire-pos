package com.fire.pos.presentation.splash.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface SplashViewModelContract : BaseViewModelContract {

    val isUserLoggedIn: LiveData<Boolean>

    fun checkUser(): Job
}