package com.fire.pos.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ViewModelProviderFactory @Inject constructor(
    private val viewModel: ViewModel
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}