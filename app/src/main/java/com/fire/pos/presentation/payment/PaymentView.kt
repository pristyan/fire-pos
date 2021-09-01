package com.fire.pos.presentation.payment


/**
 * Created by Chandra.
 **/

interface PaymentView {

    fun initView()

    fun initPayOption()

    fun getCartTotal()

    fun calculateChange()

    fun setBackStackState()

    fun observeBackPress()

    fun pay()

    fun navigateToHome()

}