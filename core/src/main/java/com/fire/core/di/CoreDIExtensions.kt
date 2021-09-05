package com.fire.core.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.fire.core.di.component.CoreComponentProvider


/**
 * Created by Chandra.
 **/

fun Activity.coreComponent() = (applicationContext as? CoreComponentProvider)?.provideCoreComponent()
    ?: throw IllegalStateException("AppComponentProvider not implemented: $applicationContext")

fun Fragment.coreComponent() = requireActivity().coreComponent()