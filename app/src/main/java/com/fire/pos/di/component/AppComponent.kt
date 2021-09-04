package com.fire.pos.di.component

import android.content.Context
import android.content.SharedPreferences
import com.fire.pos.data.repository.account.AccountRepository
import com.fire.pos.data.repository.cart.CartRepository
import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.data.repository.transaction.TransactionRepository
import com.fire.pos.data.source.local.account.AccountLocalDataSource
import com.fire.pos.di.module.AndroidContextModule
import com.fire.pos.di.module.AppModule
import com.fire.pos.data.source.local.cart.CartLocalDataSource
import com.fire.pos.data.source.remote.account.AccountRemoteDataSource
import com.fire.pos.data.source.remote.product.ProductRemoteDataSource
import com.fire.pos.data.source.remote.transaction.TransactionRemoteDataSource
import com.fire.pos.database.AppDatabase
import com.fire.pos.scheduler.SchedulerProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidContextModule::class, AppModule::class])
interface AppComponent {

    fun context(): Context

    fun gson(): Gson

    fun appDatabase(): AppDatabase

    fun sharedPreference(): SharedPreferences

    fun schedulerProvider(): SchedulerProvider

    fun firebaseAuth(): FirebaseAuth

    fun firebaseFirestore(): FirebaseFirestore

    fun firebaseStorage(): FirebaseStorage

    // repository
    fun accountRepository(): AccountRepository

    fun productRepository(): ProductRepository

    fun transactionRepository(): TransactionRepository

    fun cartRepository(): CartRepository

    // local data source
    fun accountLocalDataSource(): AccountLocalDataSource

    fun cartLocalDataSource(): CartLocalDataSource

    // remote data source
    fun accountRemoteDataSource(): AccountRemoteDataSource

    fun productRemoteDataSource(): ProductRemoteDataSource

    fun transactionRemoteDataSource(): TransactionRemoteDataSource
}
