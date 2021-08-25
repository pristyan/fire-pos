package com.fire.pos.util

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


/**
 * Created by Chandra.
 **/

suspend inline fun <T : Any> Task<T>.await(): Result<T> {
    return suspendCancellableCoroutine { coroutine ->
        try {
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    coroutine.resume(Result.success(task.result as T))
                } else {
                    coroutine.resume(
                        Result.failure(task.exception ?: Exception("Something goes wrong"))
                    )
                }
            }

            addOnCanceledListener {
                coroutine.cancel()
            }

        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure<T>(e)
        }
    }
}