package com.fire.pos.presentation.cashier.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.cashier.CashierFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [CashierModule::class], dependencies = [AppComponent::class])
interface CashierComponent: BaseFragmentComponent<CashierFragment>