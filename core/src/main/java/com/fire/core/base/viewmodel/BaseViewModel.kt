package com.fire.core.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext


/**
 * Created by Chandra.
 **/

abstract class BaseViewModel : ViewModel(), CoroutineScope, BaseViewModelContract {

    private val _isLoading = MutableLiveData<Boolean>()

    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    override fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}