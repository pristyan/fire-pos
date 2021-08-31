package com.fire.pos.presentation.transactionsummary.viewmodel

import androidx.lifecycle.LiveData
import com.fire.pos.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.ProductCart
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface TransactionSummaryViewModelContract : BaseViewModelContract {

    val cartSuccess: LiveData<List<ProductCart>>

    val totalUpdate: LiveData<String>

    val updateCartSuccess: LiveData<ProductCart>

    val deleteCartSuccess: LiveData<ProductCart>

    val errorMessage: LiveData<String>

    fun getCartList(): Job

    fun updateCart(item: ProductCart): Job

    fun deleteCart(item: ProductCart): Job

}