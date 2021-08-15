package com.fire.pos.base.fragment

import androidx.fragment.app.Fragment


/**
 * Created by Chandra.
 **/

interface BaseFragmentComponent<T: Fragment> {

    fun inject(fragment: T)

}