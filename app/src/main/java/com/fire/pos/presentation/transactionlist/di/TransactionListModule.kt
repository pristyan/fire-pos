package com.fire.pos.presentation.transactionlist.di

import androidx.lifecycle.ViewModel
import com.fire.core.di.module.BaseFeatureModule
import com.fire.pos.domain.transactionlist.TransactionListInteractor
import com.fire.pos.domain.transactionlist.TransactionListInteractorImpl
import com.fire.pos.presentation.transactionlist.viewmodel.TransactionListViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface TransactionListModule {

    @Binds
    fun bindViewModel(historyViewModel: TransactionListViewModel): ViewModel

    @Binds
    fun bindHistoryInteractor(historyInteractorImpl: TransactionListInteractorImpl): TransactionListInteractor
}