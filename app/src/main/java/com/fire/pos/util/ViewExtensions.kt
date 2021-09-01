package com.fire.pos.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


/**
 * Created by Chandra.
 **/

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Context.showMessageDialog(
    message: String? = null, title: String? = null, callback: (() -> Unit)? = null
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("OK") { _, _ -> callback?.invoke() }
        .show()
}

fun Context.showConfirmationDialog(
    title: Int, message: Int, positiveCallback: () -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("No") { _, _ -> }
        .setPositiveButton("Yes") { _, _ -> positiveCallback() }
        .show()
}