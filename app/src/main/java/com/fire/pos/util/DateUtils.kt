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

fun String.normalizeDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
    val date = formatter.parse(this)
    formatter.applyPattern("dd MMM yyyy hh:mm:ss")
    return formatter.format(date ?: Date())
}