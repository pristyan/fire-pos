package com.fire.pos

import android.app.Application
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.component.AppComponentProvider
import com.fire.pos.di.component.DaggerAppComponent
import com.fire.pos.di.module.AndroidContextModule


/**
 * Created by Chandra.
 **/

class MyApplication: Application(), AppComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun provideAppComponent(): AppComponent {
        return appComponent
    }

    private fun setupDagger() {
        appComponent = DaggerAppComponent.builder()
            .androidContextModule(AndroidContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }
}