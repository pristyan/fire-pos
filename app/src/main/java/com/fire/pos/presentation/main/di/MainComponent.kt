package com.fire.pos.presentation.main.di

import com.fire.core.base.activity.BaseActivityComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.main.MainActivity
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent: BaseActivityComponent<MainActivity>