package com.fire.pos.preference

import android.content.Context


/**
 * Created by Chandra.
 **/
class PreferenceHelper(context: Context) {

    private val prefs = context.getSharedPreferences("pos", Context.MODE_PRIVATE)

    companion object {
        private var preferenceHelper: PreferenceHelper? = null

        fun getInstance(context: Context): PreferenceHelper {
            if (preferenceHelper == null) {
                preferenceHelper = PreferenceHelper(context)
            }

            return preferenceHelper as PreferenceHelper
        }
    }

}