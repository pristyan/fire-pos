package com.fire.pos.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.fire.pos.constant.AppConstant
import com.fire.pos.database.AppDatabase
import com.fire.pos.scheduler.AppSchedulerProvider
import com.fire.pos.scheduler.SchedulerProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides


/**
 * Created by Chandra.
 **/

@Module(includes = [RepositoryModule::class, DataSourceModule::class])
object AppModule {

    @JvmStatic
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE)
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
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppConstant.DB_NAME
        ).build()
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