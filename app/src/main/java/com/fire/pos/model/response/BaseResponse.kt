package com.fire.pos.model.response

import java.io.Serializable


/**
 * Created by Chandra.
 **/

class BaseResponse<T : Any>(
    var data: T? = null,
    var throwable: Throwable? = null
) : Serializable {

    constructor(throwable: Throwable): this(
        data = null,
        throwable = throwable
    )

    val isSuccess: Boolean
        get() = throwable == null

}