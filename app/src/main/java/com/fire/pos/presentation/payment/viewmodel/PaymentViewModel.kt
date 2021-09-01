package com.fire.pos.presentation.payment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.pos.base.viewmodel.BaseViewModel
import com.fire.pos.domain.payment.PaymentInteractor
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.ProductCart
import com.fire.pos.scheduler.SchedulerProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class PaymentViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val paymentInteractor: PaymentInteractor
) : BaseViewModel(), PaymentViewModelContract {

    private val _cartTotal = MutableLiveData<Long>()
    override val cartTotal: LiveData<Long>
        get() = _cartTotal

    private val _paymentSuccess = MutableLiveData<Boolean>()
    override val paymentSuccess: LiveData<Boolean>
        get() = _paymentSuccess

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _cartItems = MutableLiveData<List<ProductCart>>()

    override fun getCartTotal(): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            paymentInteractor.getCartList()
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> {
                val list = result.data ?: emptyList()
                val total = list.sumOf { it.qty * it.productPrice }
                _cartTotal.value = total
                _cartItems.value = list
            }
        }

        setLoading(false)
    }

    override fun pay(): Job = launch(schedulerProvider.ui()) {
        setLoading(true)
        val result = withContext(schedulerProvider.io()) {
            paymentInteractor.pay(_cartItems.value ?: emptyList())
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _paymentSuccess.value = true
        }

        setLoading(false)
    }

}