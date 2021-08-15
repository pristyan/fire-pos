package com.fire.pos.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.fire.pos.di.component.AppComponentProvider


/**
 * Created by Chandra.
 **/

fun Activity.appComponent() = (applicationContext as? AppComponentProvider)?.provideAppComponent()
    ?: throw IllegalStateException("AppComponentProvider not implemented: $applicationContext")

fun Fragment.appComponent() = requireActivity().appComponent()