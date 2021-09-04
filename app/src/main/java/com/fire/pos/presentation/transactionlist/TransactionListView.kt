package com.fire.pos.presentation.transactionlist


/**
 * Created by Chandra.
 **/

interface TransactionListView {

    fun initView()

    fun observeBackStack()

    fun navigateToDetail(id: String)

    fun getTransactionList()

    fun chooseStartDate()

    fun chooseEndDate()

}