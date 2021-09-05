package com.fire.core.util

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import com.fire.core.model.Result

/**
 * Created by Chandra.
 **/

suspend inline fun <T : Any> Task<T>.await(): Result<T> {
    return suspendCancellableCoroutine { coroutine ->
        try {
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    coroutine.resume(Result.Success(task.result))
                } else {
                    coroutine.resume(
                        Result.Error(task.exception?.message ?: "Something goes wrong")
                    )
                }
            }

            addOnCanceledListener {
                coroutine.cancel()
            }

        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(e.message ?: "Something goes wrong")
        }
    }
}