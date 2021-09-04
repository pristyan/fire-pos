package com.fire.pos.presentation.transactionlist.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.transactionlist.TransactionListFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [TransactionListModule::class], dependencies = [AppComponent::class])
interface TransactionListComponent : BaseFragmentComponent<TransactionListFragment>