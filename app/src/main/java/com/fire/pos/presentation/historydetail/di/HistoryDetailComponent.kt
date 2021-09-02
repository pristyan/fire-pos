package com.fire.pos.presentation.historydetail.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.historydetail.HistoryDetailFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [HistoryDetailModule::class], dependencies = [AppComponent::class])
interface HistoryDetailComponent: BaseFragmentComponent<HistoryDetailFragment>