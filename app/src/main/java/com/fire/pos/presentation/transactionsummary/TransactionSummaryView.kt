package com.fire.pos.presentation.transactionsummary


/**
 * Created by Chandra.
 **/

interface TransactionSummaryView {

    fun initView()

    fun getCartList()

    fun navigateBack()

    fun observeNavigation()

}