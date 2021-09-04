package com.fire.pos.presentation.cart.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.cart.CartFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [CartModule::class], dependencies = [AppComponent::class])
interface CartComponent: BaseFragmentComponent<CartFragment>