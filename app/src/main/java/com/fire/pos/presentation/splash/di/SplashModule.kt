package com.fire.pos.presentation.splash.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.splash.SplashInteractor
import com.fire.pos.domain.splash.SplashInteractorImpl
import com.fire.pos.presentation.splash.viewmodel.SplashViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface SplashModule {

    @Binds
    fun bindViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    fun bindSplashInteractor(splashInteractorImpl: SplashInteractorImpl): SplashInteractor
}