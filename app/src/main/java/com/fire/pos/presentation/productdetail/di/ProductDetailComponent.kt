package com.fire.pos.presentation.productdetail.di

import com.fire.core.base.fragment.BaseFragmentComponent
import com.fire.pos.di.component.AppComponent
import com.fire.core.di.scope.FeatureScope
import com.fire.pos.presentation.productdetail.ProductDetailFragment
import dagger.Component


/**
 * Created by Chandra.
 **/

@FeatureScope
@Component(modules = [ProductDetailModule::class], dependencies = [AppComponent::class])
interface ProductDetailComponent: BaseFragmentComponent<ProductDetailFragment>