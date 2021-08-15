package com.fire.pos.base.viewmodel

import androidx.lifecycle.LiveData


/**
 * Created by Chandra.
 **/

interface BaseViewModelContract {

    val isLoading: LiveData<Boolean>

    fun setLoading(isLoading: Boolean)

}