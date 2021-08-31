package com.fire.pos.presentation.transactionsummary.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.transactionsummary.TransactionSummaryFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [TransactionSummaryModule::class], dependencies = [AppComponent::class])
interface TransactionSummaryComponent: BaseFragmentComponent<TransactionSummaryFragment>