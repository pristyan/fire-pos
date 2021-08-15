package com.fire.pos.di.module

import com.fire.pos.domain.repository.local.CartLocalDataSource
import com.fire.pos.domain.repository.local.CartLocalRepository
import com.fire.pos.domain.repository.remote.AccountRemoteDataSource
import com.fire.pos.domain.repository.remote.AccountRemoteRepository
import com.fire.pos.domain.repository.remote.ProductRemoteDataSource
import com.fire.pos.domain.repository.remote.ProductRemoteRepository
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module
interface RepositoryModule {

    @Binds
    fun bindCartLocalDataSource(cartLocalRepository: CartLocalRepository): CartLocalDataSource

    @Binds
    fun bindAccountRemoteDataSource(
        accountRemoteRepository: AccountRemoteRepository
    ): AccountRemoteDataSource

    @Binds
    fun bindProductRemoteDataSource(
        productRemoteRepository: ProductRemoteRepository
    ): ProductRemoteDataSource

}