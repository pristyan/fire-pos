package com.fire.pos.presentation.historydetail.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.historydetail.HistoryDetailInteractor
import com.fire.pos.domain.historydetail.HistoryDetailInteractorImpl
import com.fire.pos.presentation.historydetail.viewmodel.HistoryDetailViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface HistoryDetailModule {

    @Binds
    fun bindViewModel(historyDetailViewModel: HistoryDetailViewModel): ViewModel

    @Binds
    fun bindHistoryDetailInteractor(
        historyDetailInteractorImpl: HistoryDetailInteractorImpl
    ): HistoryDetailInteractor
}