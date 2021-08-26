package com.fire.pos.util

import com.fire.pos.constant.ResponseConstant
import com.fire.pos.model.response.BaseResponse


/**
 * Created by Chandra.
 **/

fun <T: Any> BaseResponse<T>.getErrorMessage(): String {
    return throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG_INTERACTOR
}