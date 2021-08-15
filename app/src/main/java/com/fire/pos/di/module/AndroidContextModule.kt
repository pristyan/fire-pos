package com.fire.pos.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidContextModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }
}
