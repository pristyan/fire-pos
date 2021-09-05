package com.fire.core.di.module

import android.content.Context
import android.content.SharedPreferences
import com.fire.core.constant.CoreConstant
import com.fire.core.scheduler.AppSchedulerProvider
import com.fire.core.scheduler.SchedulerProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides


/**
 * Created by Chandra.
 **/

@Module
object CoreModule {

    @JvmStatic
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    @JvmStatic
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

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