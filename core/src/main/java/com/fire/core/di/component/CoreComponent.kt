package com.fire.core.di.component

import android.content.Context
import android.content.SharedPreferences
import com.fire.core.di.module.AndroidContextModule
import com.fire.core.di.module.CoreModule
import com.fire.core.scheduler.SchedulerProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Chandra.
 **/

@Singleton
@Component(modules = [AndroidContextModule::class, CoreModule::class])
interface CoreComponent {

    fun context(): Context

    fun gson(): Gson

    fun sharedPreference(): SharedPreferences

    fun schedulerProvider(): SchedulerProvider

    fun firebaseAuth(): FirebaseAuth

    fun firebaseFirestore(): FirebaseFirestore

    fun firebaseStorage(): FirebaseStorage

}
