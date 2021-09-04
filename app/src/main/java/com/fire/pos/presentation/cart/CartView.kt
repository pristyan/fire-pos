package com.fire.pos.presentation.cart


/**
 * Created by Chandra.
 **/

interface CartView {

    fun initView()

    fun getCartList()

    fun setBackStackState()

    fun observeNavigation()

    fun navigateToPayment()

}