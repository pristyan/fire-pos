package com.fire.pos.presentation.home.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.base.viewmodel.ViewModelProviderFactory
import com.fire.pos.presentation.home.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module
interface HomeModule {

    @Binds
    fun bindViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    fun bindViewModelProviderFactory(
        viewModelProviderFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

}