package com.fire.pos.presentation.payment.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.payment.PaymentInteractor
import com.fire.pos.domain.payment.PaymentInteractorImpl
import com.fire.pos.presentation.payment.viewmodel.PaymentViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface PaymentModule {

    @Binds
    fun bindViewModel(paymentViewModel: PaymentViewModel): ViewModel

    @Binds
    fun bindPaymentInteractor(paymentInteractorImpl: PaymentInteractorImpl): PaymentInteractor

}