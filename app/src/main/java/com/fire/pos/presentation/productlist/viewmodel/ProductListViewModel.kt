package com.fire.pos.presentation.productlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.core.model.Result
import com.fire.pos.model.view.Product
import com.fire.pos.domain.productlist.ProductListInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val productListInteractor: ProductListInteractor
): BaseViewModel(), ProductListViewModelContract {

    private val _productListSuccess = MutableLiveData<List<Product>>()
    override val productListSuccess: LiveData<List<Product>> = _productListSuccess

    private val _productListError = MutableLiveData<String>()
    override val productListError: LiveData<String> = _productListError

    override fun getProductList(): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            productListInteractor.getProducts()
        }

        when (result) {
            is Result.Error -> _productListError.value = result.message
            is Result.Success -> _productListSuccess.value = result.data
        }

        setLoading(false)
    }


}