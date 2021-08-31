package com.fire.pos.presentation.transaction.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.transaction.TransactionFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [TransactionModule::class], dependencies = [AppComponent::class])
interface TransactionComponent: BaseFragmentComponent<TransactionFragment>