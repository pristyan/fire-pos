package com.fire.pos.util

import com.fire.pos.constant.ResponseConstant
import com.fire.pos.model.response.BaseResponse
import com.fire.pos.model.response.Result


/**
 * Created by Chandra.
 **/

fun <E : Any, V : Any> BaseResponse<E>.toResultBuilder(): ResultBuilder<E, V> {
    return ResultBuilder(this)
}

class ResultBuilder<E : Any, V : Any>(private val response: BaseResponse<E>) {

    private var result: Result<V>? = null

    fun setData(callback: (E?) -> V): ResultBuilder<E, V> {
        if (response.isSuccess) {
            result = Result.Success(callback(response.data))
        }
        return this
    }

    fun build(): Result<V> {
        return result ?: Result.Error(
            response.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG_DOMAIN
        )
    }
}