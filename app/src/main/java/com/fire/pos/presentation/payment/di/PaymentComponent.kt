package com.fire.pos.presentation.payment.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.payment.PaymentFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [PaymentModule::class], dependencies = [AppComponent::class])
interface PaymentComponent: BaseFragmentComponent<PaymentFragment>