package com.fire.pos.presentation.history


/**
 * Created by Chandra.
 **/

interface HistoryView {

    fun initView()

    fun observeBackStack()

    fun navigateToDetail(id: String)

    fun getTransactionList()

}