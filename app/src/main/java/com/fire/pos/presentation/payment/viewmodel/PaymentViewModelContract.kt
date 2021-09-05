package com.fire.pos.presentation.payment.viewmodel

import androidx.lifecycle.LiveData
import com.fire.core.base.viewmodel.BaseViewModelContract
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface PaymentViewModelContract : BaseViewModelContract {

    val cartTotal: LiveData<Long>

    val paymentSuccess: LiveData<Boolean>

    val errorMessage: LiveData<String>

    fun getCartTotal(): Job

    fun pay(): Job

}