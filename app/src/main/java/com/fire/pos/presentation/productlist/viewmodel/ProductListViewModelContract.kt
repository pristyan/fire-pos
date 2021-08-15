package com.fire.pos.presentation.productlist.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import com.fire.pos.data.view.Product
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface ProductListViewModelContract : BaseViewModelContract {

    val productListSuccess: LiveData<List<Product>>

    val productListError: LiveData<String>

    fun getProductList(): Job

}