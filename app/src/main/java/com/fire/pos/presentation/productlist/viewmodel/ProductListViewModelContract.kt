package com.fire.pos.presentation.productlist.viewmodel

import androidx.lifecycle.LiveData
import com.fire.core.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.Product
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface ProductListViewModelContract : BaseViewModelContract {

    val productListSuccess: LiveData<List<Product>>

    val productListError: LiveData<String>

    fun getProductList(): Job

}