package com.fire.pos.presentation.registration.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.base.viewmodel.ViewModelProviderFactory
import com.fire.pos.domain.interactor.registration.RegistrationInteractor
import com.fire.pos.domain.interactor.registration.RegistrationInteractorImpl
import com.fire.pos.presentation.registration.viewmodel.RegistrationViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module
interface RegistrationModule {

    @Binds
    fun bindViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @Binds
    fun bindViewModelProviderFactory(
        viewModelProviderFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    fun bindRegistrationInteractor(
        registrationInteractorImpl: RegistrationInteractorImpl
    ): RegistrationInteractor
}