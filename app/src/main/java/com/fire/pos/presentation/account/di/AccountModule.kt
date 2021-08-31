package com.fire.pos.presentation.account.di

import androidx.lifecycle.ViewModel
import com.fire.pos.di.module.BaseFeatureModule
import com.fire.pos.presentation.account.viewmodel.AccountViewModel
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module(includes = [BaseFeatureModule::class])
interface AccountModule {

    @Binds
    fun bindViewModel(accountViewModel: AccountViewModel): ViewModel

}