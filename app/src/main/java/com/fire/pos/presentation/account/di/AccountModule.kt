package com.fire.pos.presentation.account.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.base.viewmodel.ViewModelProviderFactory
import com.fire.pos.presentation.account.viewmodel.AccountViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module
interface AccountModule {

    @Binds
    fun bindViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    fun bindViewModelProviderFactory(
        viewModelProviderFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

}