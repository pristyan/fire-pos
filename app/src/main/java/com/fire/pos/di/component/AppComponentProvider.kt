package com.fire.pos.di.component

import com.fire.core.di.component.CoreComponent


/**
 * Created by Chandra.
 **/

object AppComponentProvider {

    private var appComponent: AppComponent? = null

    fun provide(coreComponent: CoreComponent): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                .coreComponent(coreComponent)
                .build()
        }

        return appComponent as AppComponent
    }
}