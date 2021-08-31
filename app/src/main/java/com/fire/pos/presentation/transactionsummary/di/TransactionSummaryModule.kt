package com.fire.pos.presentation.transactionsummary.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.transactionsummary.TransactionSummaryInteractor
import com.fire.pos.domain.transactionsummary.TransactionSummaryInteractorImpl
import com.fire.pos.presentation.transactionsummary.viewmodel.TransactionSummaryViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface TransactionSummaryModule {

    @Binds
    fun bindViewModel(transactionSummaryViewModel: TransactionSummaryViewModel): ViewModel

    @Binds
    fun bindTransactionSummaryInteractor(
        transactionSummaryInteractorImpl: TransactionSummaryInteractorImpl
    ): TransactionSummaryInteractor
}