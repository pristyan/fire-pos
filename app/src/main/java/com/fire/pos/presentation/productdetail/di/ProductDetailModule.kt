package com.fire.pos.presentation.productdetail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.base.viewmodel.ViewModelProviderFactory
import com.fire.pos.domain.interactor.productdetail.ProductDetailInteractor
import com.fire.pos.domain.interactor.productdetail.ProductDetailInteractorImpl
import com.fire.pos.presentation.productdetail.viewmodel.ProductDetailViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module
interface ProductDetailModule {

    @Binds
    fun bindViewModel(productDetailViewModel: ProductDetailViewModel): ViewModel

    @Binds
    fun bindViewModelProviderFactory(
        viewModelProviderFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    fun bindProductDetailInteractor(
        productDetailInteractorImpl: ProductDetailInteractorImpl
    ): ProductDetailInteractor
}