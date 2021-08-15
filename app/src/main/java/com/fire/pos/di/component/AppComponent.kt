package com.fire.pos.di.component

import android.content.Context
import com.fire.pos.di.module.AndroidContextModule
import com.fire.pos.di.module.AppModule
import com.fire.pos.domain.repository.local.CartLocalDataSource
import com.fire.pos.domain.repository.remote.AccountRemoteDataSource
import com.fire.pos.domain.repository.remote.ProductRemoteDataSource
import com.fire.pos.scheduler.SchedulerProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidContextModule::class, AppModule::class])
interface AppComponent {

    fun context(): Context

    fun schedulerProvider(): SchedulerProvider

    fun firebaseAuth(): FirebaseAuth

    fun firebaseFirestore(): FirebaseFirestore

    fun firebaseStorage(): FirebaseStorage

    fun cartLocalDataSource(): CartLocalDataSource

    fun accountRemoteDataSource(): AccountRemoteDataSource

    fun productRemoteDataSource(): ProductRemoteDataSource

}
