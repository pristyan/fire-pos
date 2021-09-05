package com.fire.pos.presentation.login.di

import com.fire.core.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.login.LoginFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [LoginModule::class], dependencies = [AppComponent::class])
interface LoginComponent: BaseFragmentComponent<LoginFragment>