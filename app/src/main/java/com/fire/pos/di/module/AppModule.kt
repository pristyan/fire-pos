package com.fire.pos.di.module

import com.fire.pos.scheduler.AppSchedulerProvider
import com.fire.pos.scheduler.SchedulerProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides


/**
 * Created by Chandra.
 **/

@Module(includes = [RepositoryModule::class])
object AppModule {

    @JvmStatic
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @JvmStatic
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @JvmStatic
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @JvmStatic
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

}