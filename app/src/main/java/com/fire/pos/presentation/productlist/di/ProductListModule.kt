package com.fire.pos.presentation.productlist.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.productlist.ProductListInteractor
import com.fire.pos.domain.productlist.ProductListInteractorImpl
import com.fire.pos.presentation.productlist.viewmodel.ProductListViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface ProductListModule {

    @Binds
    fun bindViewModel(productListViewModel: ProductListViewModel): ViewModel

    @Binds
    fun bindProductListInteractor(
        productListInteractorImpl: ProductListInteractorImpl
    ): ProductListInteractor
}