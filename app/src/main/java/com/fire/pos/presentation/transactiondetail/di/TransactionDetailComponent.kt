package com.fire.pos.presentation.transactiondetail.di

import com.fire.core.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.transactiondetail.TransactionDetailFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [TransactionDetailModule::class], dependencies = [AppComponent::class])
interface TransactionDetailComponent: BaseFragmentComponent<TransactionDetailFragment>