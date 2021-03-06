package com.fire.pos.presentation.splash.di

import com.fire.core.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.splash.SplashFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [SplashModule::class], dependencies = [AppComponent::class])
interface SplashComponent: BaseFragmentComponent<SplashFragment>