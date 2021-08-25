package com.fire.pos.presentation.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.base.viewmodel.ViewModelProviderFactory
import com.fire.pos.domain.login.LoginInteractor
import com.fire.pos.domain.login.LoginInteractorImpl
import com.fire.pos.presentation.login.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module
interface LoginModule {

    @Binds
    fun bindViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    fun bindViewModelProviderFactory(
        viewModelProviderFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    fun bindLoginInteractor(loginInteractorImpl: LoginInteractorImpl): LoginInteractor
}