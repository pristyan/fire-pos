package com.fire.pos.presentation.history.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.history.HistoryFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [HistoryModule::class], dependencies = [AppComponent::class])
interface HistoryComponent : BaseFragmentComponent<HistoryFragment>