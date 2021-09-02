package com.fire.pos.presentation.historydetail


/**
 * Created by Chandra.
 **/

interface HistoryDetailView {

    fun initView()

    fun getTransactionId(): String

    fun getTransactionDetail()

}