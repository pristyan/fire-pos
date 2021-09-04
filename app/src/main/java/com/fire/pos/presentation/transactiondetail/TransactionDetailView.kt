package com.fire.pos.presentation.transactiondetail


/**
 * Created by Chandra.
 **/

interface TransactionDetailView {

    fun initView()

    fun getTransactionId(): String

    fun getTransactionDetail()

}