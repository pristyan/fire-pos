package com.fire.pos.presentation.main.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.presentation.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface MainModule {

    @Binds
    fun bindViewModel(mainViewModel: MainViewModel): ViewModel

}