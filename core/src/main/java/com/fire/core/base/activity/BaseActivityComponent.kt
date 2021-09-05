package com.fire.core.base.activity

import android.app.Activity


/**
 * Created by Chandra.
 **/

interface BaseActivityComponent<T : Activity> {
    fun inject(activity: T)
}