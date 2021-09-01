package com.fire.pos.util

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Chandra.
 **/

fun getCurrentDateTime(): String {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
    return formatter.format(calendar.time)
}