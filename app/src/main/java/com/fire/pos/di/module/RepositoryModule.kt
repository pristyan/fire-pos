package com.fire.pos.di.module

import com.fire.pos.data.repository.account.AccountRepository
import com.fire.pos.data.repository.account.AccountRepositoryImpl
import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.data.repository.product.ProductRepositoryImpl
import com.fire.pos.data.repository.transaction.TransactionRepository
import com.fire.pos.data.repository.transaction.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module


/**
 * Created by Chandra.
 **/
@Module
interface RepositoryModule {

    @Binds
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository
}