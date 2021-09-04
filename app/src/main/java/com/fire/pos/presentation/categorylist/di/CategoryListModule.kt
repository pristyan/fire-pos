package com.fire.pos.presentation.categorylist.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.domain.categorylist.CategoryListInteractor
import com.fire.pos.domain.categorylist.CategoryListInteractorImpl
import com.fire.pos.presentation.categorylist.viewmodel.CategoryListViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface CategoryListModule {

    @Binds
    fun bindViewModel(categoryListViewModel: CategoryListViewModel): ViewModel

    @Binds
    fun bindCategoryListInteractor(
        categoryListInteractorImpl: CategoryListInteractorImpl
    ): CategoryListInteractor

}