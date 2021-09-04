package com.fire.pos.di.module

import com.fire.pos.data.source.local.account.AccountLocalDataSource
import com.fire.pos.data.source.local.account.AccountLocalDataSourceImpl
import com.fire.pos.data.source.local.cart.CartLocalDataSource
import com.fire.pos.data.source.local.cart.CartLocalDataSourceImpl
import com.fire.pos.data.source.remote.account.AccountRemoteDataSource
import com.fire.pos.data.source.remote.account.AccountRemoteDataSourceImpl
import com.fire.pos.data.source.remote.category.CategoryRemoteDataSource
import com.fire.pos.data.source.remote.category.CategoryRemoteDataSourceImpl
import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.data.source.remote.product.ProductRemoteDataSourceImpl
import com.fire.pos.data.source.remote.transaction.TransactionRemoteDataSource
import com.fire.pos.data.source.remote.transaction.TransactionRemoteDataSourceImpl
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
    fun bindAccountRemoteDataSource(
        accountRemoteDataSourceImpl: AccountRemoteDataSourceImpl
    ): AccountRemoteDataSource

    @Binds
    fun bindProductRemoteDataSource(
        productRemoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource

    @Binds
    fun bindTransactionDataSource(
        transactionDataSourceImpl: TransactionRemoteDataSourceImpl
    ): TransactionRemoteDataSource

    @Binds
    fun bindCategoryRemoteDataSource(
        categoryRemoteDataSourceImpl: CategoryRemoteDataSourceImpl
    ): CategoryRemoteDataSource
}