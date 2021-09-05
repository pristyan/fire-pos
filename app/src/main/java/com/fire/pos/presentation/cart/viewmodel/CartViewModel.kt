package com.fire.pos.presentation.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.pos.domain.cart.CartInteractor
import com.fire.core.model.Result
import com.fire.pos.model.view.ProductCart
import com.fire.core.util.toIDR
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CartViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val cartInteractor: CartInteractor
): BaseViewModel(), CartViewModelContract {

    private val _cartSuccess = MutableLiveData<List<ProductCart>>()
    override val cartSuccess: LiveData<List<ProductCart>>
        get() = _cartSuccess

    private val _totalUpdate = MutableLiveData<String>()
    override val totalUpdate: LiveData<String>
        get() = _totalUpdate

    private val _updateCartSuccess = MutableLiveData<ProductCart>()
    override val updateCartSuccess: LiveData<ProductCart>
        get() = _updateCartSuccess

    private val _deleteCartSuccess = MutableLiveData<ProductCart>()
    override val deleteCartSuccess: LiveData<ProductCart>
        get() = _deleteCartSuccess

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private fun updateTotalPrice(item: ProductCart?) {
        item?.let { product ->
            _cartSuccess.value
                ?.firstOrNull { it.productId == product.productId }
                ?.apply { qty = product.qty }
        }

        val sum: Long = cartSuccess.value?.sumOf { it.qty * it.productPrice } ?: 0L
        _totalUpdate.value = sum.toIDR()
    }

    override fun getCartList(): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            cartInteractor.getCartList()
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> {
                _cartSuccess.value = result.data
                updateTotalPrice(null)
            }
        }

        setLoading(false)
    }

    override fun updateCart(item: ProductCart): Job = launch(schedulerProvider.ui()) {
        val result = withContext(schedulerProvider.io()) {
            cartInteractor.updateCart(item)
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> {
                _updateCartSuccess.value = result.data
                updateTotalPrice(result.data)
            }
        }
    }

    override fun deleteCart(item: ProductCart): Job = launch(schedulerProvider.ui()) {
        val result = withContext(schedulerProvider.io()) {
            cartInteractor.deleteCart(item)
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> {
                item.id = ""
                item.qty = 0
                _deleteCartSuccess.value = item
                updateTotalPrice(item)
            }
        }
    }

}