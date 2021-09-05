package com.fire.pos.presentation.account.di

import com.fire.core.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.account.AccountFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [AccountModule::class], dependencies = [AppComponent::class])
interface AccountComponent: BaseFragmentComponent<AccountFragment>