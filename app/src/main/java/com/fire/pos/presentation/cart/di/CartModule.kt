package com.fire.pos.presentation.cart.di

import androidx.lifecycle.ViewModel
import com.fire.core.di.module.BaseFeatureModule
import com.fire.pos.domain.cart.CartInteractor
import com.fire.pos.domain.cart.CartInteractorImpl
import com.fire.pos.presentation.cart.viewmodel.CartViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface CartModule {

    @Binds
    fun bindViewModel(transactionSummaryViewModel: CartViewModel): ViewModel

    @Binds
    fun bindTransactionSummaryInteractor(
        transactionSummaryInteractorImpl: CartInteractorImpl
    ): CartInteractor
}