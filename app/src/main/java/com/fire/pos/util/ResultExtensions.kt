package com.fire.pos.util

import com.fire.pos.constant.ResponseConstant


/**
 * Created by Chandra.
 **/

fun <T> Result<T>.getException(): Throwable {
    return exceptionOrNull() ?: Throwable(ResponseConstant.SOMETHING_GOES_WRONG_REPO)
}
