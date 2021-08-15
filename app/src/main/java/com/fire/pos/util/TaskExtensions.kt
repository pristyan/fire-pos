package com.fire.pos.util

import com.fire.pos.data.response.BaseResponse
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


/**
 * Created by Chandra.
 **/

suspend inline fun <T : Any> Task<T>.await(): BaseResponse<T> {
    return suspendCancellableCoroutine { coroutine ->
        try {
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    coroutine.resume(
                        BaseResponse(
                            data = task.result,
                            throwable = null
                        )
                    )
                } else {
                    coroutine.resume(
                        BaseResponse(
                            data = null,
                            throwable = task.exception ?: Exception("Something goes wrong")
                        )
                    )
                }
            }

            addOnCanceledListener {
                coroutine.cancel()
            }

        } catch (exception: Exception) {
            exception.printStackTrace()
            BaseResponse(
                data = null,
                throwable = exception
            )
        }
    }
}