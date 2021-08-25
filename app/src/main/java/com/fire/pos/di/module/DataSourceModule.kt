package com.fire.pos.di.module

import com.fire.pos.data.source.local.account.AccountLocalDataSource
import com.fire.pos.data.source.local.account.AccountLocalDataSourceImpl
import com.fire.pos.data.source.local.cart.CartLocalDataSource
import com.fire.pos.data.source.local.cart.CartLocalDataSourceImpl
import com.fire.pos.data.source.local.product.ProductLocalDataSource
import com.fire.pos.data.source.local.product.ProductLocalDataSourceImpl
import com.fire.pos.data.source.remote.account.AccountRemoteDataSource
import com.fire.pos.data.source.remote.account.AccountRemoteDataSourceImpl
import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.data.source.remote.product.ProductRemoteDataSourceImpl
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/

@Module
interface DataSourceModule {

    @Binds
    fun bindAccountLocalDataSource(
        accountLocalDataSourceImpl: AccountLocalDataSourceImpl
    ): AccountLocalDataSource

    @Binds
    fun bindCartLocalDataSource(
        cartLocalDataSourceImpl: CartLocalDataSourceImpl
    ): CartLocalDataSource

    @Binds
    fun bindProductLocalDataSource(
        productLocalDataSourceImpl: ProductLocalDataSourceImpl
    ): ProductLocalDataSource

    @Binds
    fun bindAccountRemoteDataSource(
        accountRemoteDataSourceImpl: AccountRemoteDataSourceImpl
    ): AccountRemoteDataSource

    @Binds
    fun bindProductRemoteDataSource(
        productRemoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource

}