package com.fire.pos

import android.app.Application
import com.fire.core.di.component.CoreComponent
import com.fire.core.di.component.CoreComponentProvider
import com.fire.core.di.component.DaggerCoreComponent
import com.fire.core.di.module.AndroidContextModule


/**
 * Created by Chandra.
 **/

class MyApplication: Application(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun provideCoreComponent(): CoreComponent {
        return coreComponent
    }

    private fun setupDagger() {
        coreComponent = DaggerCoreComponent.builder()
            .androidContextModule(AndroidContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }
}