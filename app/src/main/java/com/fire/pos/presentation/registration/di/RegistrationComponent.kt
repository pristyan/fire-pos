package com.fire.pos.presentation.registration.di

import com.fire.core.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.registration.RegistrationFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [RegistrationModule::class], dependencies = [AppComponent::class])
interface RegistrationComponent: BaseFragmentComponent<RegistrationFragment>