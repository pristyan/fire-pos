package com.fire.pos.presentation.productlist.di

import com.fire.pos.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.scope.FeatureScope
import com.fire.pos.presentation.productlist.ProductListFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [ProductListModule::class], dependencies = [AppComponent::class])
interface ProductListComponent: BaseFragmentComponent<ProductListFragment>