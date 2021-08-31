package com.fire.pos.presentation.home.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.presentation.home.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface HomeModule {

    @Binds
    fun bindViewModel(homeViewModel: HomeViewModel): ViewModel

}