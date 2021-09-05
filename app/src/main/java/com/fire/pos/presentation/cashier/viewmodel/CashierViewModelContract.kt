package com.fire.pos.presentation.cashier.viewmodel

import androidx.lifecycle.LiveData
import com.fire.core.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.ProductCart
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface CashierViewModelContract : BaseViewModelContract {

    val productListSuccess: LiveData<List<ProductCart>>

    val errorMessage: LiveData<String>

    val addCartSuccess: LiveData<ProductCart>

    val updateCartSuccess: LiveData<ProductCart>

    val deleteCartSuccess: LiveData<ProductCart>

    val showCartModal: LiveData<Pair<Boolean, String>>

    fun getProductList(): Job

    fun addCart(item: ProductCart): Job

    fun updateCart(item: ProductCart): Job

    fun deleteCart(item: ProductCart): Job

}