package com.fire.pos.presentation.transactiondetail.di

import androidx.lifecycle.ViewModel
import com.fire.core.di.module.BaseFeatureModule
import com.fire.pos.domain.transactiondetail.TransactionDetailInteractor
import com.fire.pos.domain.transactiondetail.TransactionDetailInteractorImpl
import com.fire.pos.presentation.transactiondetail.viewmodel.TransactionDetailViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface TransactionDetailModule {

    @Binds
    fun bindViewModel(historyDetailViewModel: TransactionDetailViewModel): ViewModel

    @Binds
    fun bindHistoryDetailInteractor(
        historyDetailInteractorImpl: TransactionDetailInteractorImpl
    ): TransactionDetailInteractor
}