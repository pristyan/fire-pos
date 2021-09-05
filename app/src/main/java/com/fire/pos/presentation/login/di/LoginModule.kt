package com.fire.pos.presentation.login.di

import androidx.lifecycle.ViewModel
import com.fire.core.di.module.BaseFeatureModule
import com.fire.pos.domain.login.LoginInteractor
import com.fire.pos.domain.login.LoginInteractorImpl
import com.fire.pos.presentation.login.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface LoginModule {

    @Binds
    fun bindViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    fun bindLoginInteractor(loginInteractorImpl: LoginInteractorImpl): LoginInteractor
}