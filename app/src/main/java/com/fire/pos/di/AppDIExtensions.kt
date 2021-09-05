package com.fire.pos.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.fire.core.di.coreComponent
import com.fire.pos.di.component.AppComponent
import com.fire.pos.di.component.AppComponentProvider


/**
 * Created by Chandra.
 **/

fun Activity.appComponent(): AppComponent = AppComponentProvider.provide(coreComponent())

fun Fragment.appComponent() = requireActivity().appComponent()