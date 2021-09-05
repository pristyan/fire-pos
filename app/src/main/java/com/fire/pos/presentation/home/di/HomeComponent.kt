package com.fire.pos.presentation.home.di

import com.fire.core.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.home.HomeFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [HomeModule::class], dependencies = [AppComponent::class])
interface HomeComponent: BaseFragmentComponent<HomeFragment>