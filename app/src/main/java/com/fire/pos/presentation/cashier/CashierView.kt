package com.fire.pos.presentation.cashier


/**
 * Created by Chandra.
 **/

interface CashierView {

    fun initView()

    fun getProductList()

    fun observeNavigation()

    fun navigateToSummary()

    fun searchProduct(keyword: String?): Boolean
}