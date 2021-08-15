package com.fire.pos.presentation.login


/**
 * Created by Chandra.
 **/

interface LoginView {

    fun initView()

    fun checkUserSession()

    fun login()

    fun navigateToRegistration()

    fun navigateToMain()

}