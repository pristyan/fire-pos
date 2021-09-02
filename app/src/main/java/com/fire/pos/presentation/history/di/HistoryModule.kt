package com.fire.pos.presentation.history.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.history.HistoryInteractor
import com.fire.pos.domain.history.HistoryInteractorImpl
import com.fire.pos.presentation.history.viewmodel.HistoryViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface HistoryModule {

    @Binds
    fun bindViewModel(historyViewModel: HistoryViewModel): ViewModel

    @Binds
    fun bindHistoryInteractor(historyInteractorImpl: HistoryInteractorImpl): HistoryInteractor
}