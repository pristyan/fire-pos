package com.fire.pos.presentation.cashier.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.pos.domain.cashier.CashierInteractor
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

class CashierViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val cashierInteractor: CashierInteractor
): BaseViewModel(), CashierViewModelContract {

    private val _productListSuccess = MutableLiveData<List<ProductCart>>()
    override val productListSuccess: LiveData<List<ProductCart>>
        get() = _productListSuccess

    private val _addCartSuccess = MutableLiveData<ProductCart>()
    override val addCartSuccess: LiveData<ProductCart>
        get() = _addCartSuccess

    private val _updateCartSuccess = MutableLiveData<ProductCart>()
    override val updateCartSuccess: LiveData<ProductCart>
        get() = _updateCartSuccess

    private val _deleteCartSuccess = MutableLiveData<ProductCart>()
    override val deleteCartSuccess: LiveData<ProductCart>
        get() = _deleteCartSuccess

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _showCartModal = MutableLiveData<Pair<Boolean, String>>()
    override val showCartModal: LiveData<Pair<Boolean, String>>
        get() = _showCartModal

    private fun checkSummary(item: ProductCart?) {
        item?.let { product ->
            _productListSuccess.value
                ?.firstOrNull { it.productId == product.productId }
                ?.apply {
                    id = product.id
                    qty = product.qty
                }
        }

        val sum: Long = productListSuccess.value?.sumOf { it.qty * it.productPrice } ?: 0L
        _showCartModal.value = Pair((sum > 0L), sum.toIDR())
    }

    override fun getProductList(): Job = launch(schedulerProvider.ui()){
        setLoading(true)
        val result = withContext(schedulerProvider.io()) {
            cashierInteractor.getProductWithCartList()
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> {
                _productListSuccess.value = result.data
                checkSummary(null)
            }
        }

        setLoading(false)
    }

    override fun addCart(item: ProductCart): Job = launch(schedulerProvider.ui()) {
        val result = withContext(schedulerProvider.io()) {
            cashierInteractor.addCart(item)
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> {
                _addCartSuccess.value = result.data
                result.data?.let { checkSummary(it) }
            }
        }
    }

    override fun updateCart(item: ProductCart): Job = launch(schedulerProvider.ui()) {
        val result = withContext(schedulerProvider.io()) {
            cashierInteractor.updateCart(item)
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> {
                _updateCartSuccess.value = result.data
                result.data?.let { checkSummary(it) }
            }
        }
    }

    override fun deleteCart(item: ProductCart): Job = launch(schedulerProvider.ui()) {
        val result = withContext(schedulerProvider.io()) {
            cashierInteractor.deleteCart(item)
        }

        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> {
                item.id = ""
                item.qty = 0
                _deleteCartSuccess.value = item
                checkSummary(item)
            }
        }
    }
}