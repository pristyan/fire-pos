package com.fire.pos.presentation.productlist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.base.viewmodel.ViewModelProviderFactory
import com.fire.pos.domain.interactor.productlist.ProductListInteractor
import com.fire.pos.domain.interactor.productlist.ProductListInteractorImpl
import com.fire.pos.presentation.productlist.viewmodel.ProductListViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module
interface ProductListModule {

    @Binds
    fun bindViewModel(productListViewModel: ProductListViewModel): ViewModel

    @Binds
    fun bindViewModelProviderFactory(
        viewModelProviderFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    fun bindProductListInteractor(
        productListInteractorImpl: ProductListInteractorImpl
    ): ProductListInteractor
}