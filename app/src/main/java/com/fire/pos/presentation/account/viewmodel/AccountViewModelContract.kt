package com.fire.pos.presentation.account.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface AccountViewModelContract: BaseViewModelContract {

    val logoutSuccess: LiveData<Boolean>

    fun logout(): Job

}