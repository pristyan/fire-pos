package com.fire.pos.presentation.transaction


/**
 * Created by Chandra.
 **/

interface TransactionView {

    fun initView()

    fun getProductList()

    fun observeNavigation()

    fun navigateToSummary()

}