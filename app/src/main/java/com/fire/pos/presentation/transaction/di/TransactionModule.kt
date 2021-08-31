package com.fire.pos.presentation.transaction.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.transaction.TransactionInteractor
import com.fire.pos.domain.transaction.TransactionInteractorImpl
import com.fire.pos.presentation.transaction.viewmodel.TransactionViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface TransactionModule {

    @Binds
    fun bindViewModel(transactionViewModel: TransactionViewModel): ViewModel

    @Binds
    fun bindTransactionInteractor(
        transactionInteractorImpl: TransactionInteractorImpl
    ): TransactionInteractor

}