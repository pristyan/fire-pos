package com.fire.pos.presentation.registration.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.registration.RegistrationInteractor
import com.fire.pos.domain.registration.RegistrationInteractorImpl
import com.fire.pos.presentation.registration.viewmodel.RegistrationViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface RegistrationModule {

    @Binds
    fun bindViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @Binds
    fun bindRegistrationInteractor(
        registrationInteractorImpl: RegistrationInteractorImpl
    ): RegistrationInteractor
}