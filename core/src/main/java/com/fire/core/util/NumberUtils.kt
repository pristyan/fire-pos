package com.fire.core.util

import java.text.NumberFormat
import java.util.*


/**
 * Created by Chandra.
 **/

fun Long.toIDR(): String {
    return try {
        val formatter = NumberFormat.getInstance(Locale.US)
        formatter.maximumFractionDigits = 0
        "Rp " + formatter.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        "Rp 0"
    }
}