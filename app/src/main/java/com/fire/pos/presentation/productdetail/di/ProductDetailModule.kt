package com.fire.pos.presentation.productdetail.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.productdetail.ProductDetailInteractor
import com.fire.pos.domain.productdetail.ProductDetailInteractorImpl
import com.fire.pos.presentation.productdetail.viewmodel.ProductDetailViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface ProductDetailModule {

    @Binds
    fun bindViewModel(productDetailViewModel: ProductDetailViewModel): ViewModel

    @Binds
    fun bindProductDetailInteractor(
        productDetailInteractorImpl: ProductDetailInteractorImpl
    ): ProductDetailInteractor
}