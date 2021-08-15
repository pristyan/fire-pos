package com.fire.pos.data.response

import java.io.Serializable


/**
 * Created by Chandra.
 **/

class BaseResponse<out T: Any>(val data: T?, val throwable: Throwable?): Serializable {

    val isSuccess: Boolean
        get() = throwable == null

}