package com.fire.pos.presentation.categorylist.di

import com.fire.core.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.categorylist.CategoryListFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [CategoryListModule::class], dependencies = [AppComponent::class])
interface CategoryListComponent: BaseFragmentComponent<CategoryListFragment>