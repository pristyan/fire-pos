package com.fire.pos.presentation.cashier.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.cashier.CashierInteractor
import com.fire.pos.domain.cashier.CashierInteractorImpl
import com.fire.pos.presentation.cashier.viewmodel.CashierViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface CashierModule {

    @Binds
    fun bindViewModel(transactionViewModel: CashierViewModel): ViewModel

    @Binds
    fun bindTransactionInteractor(
        transactionInteractorImpl: CashierInteractorImpl
    ): CashierInteractor

}