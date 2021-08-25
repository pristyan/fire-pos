package com.fire.pos.util

import com.fire.pos.constant.ResponseConstant
import com.fire.pos.model.response.BaseResponse


/**
 * Created by Chandra.
 **/

/*
* result.toBaseResponseBuilder()
* .convert<NewData> { NewData(it) }
* .addOnSuccess { }
* .addOnFailure { }
* .build()
* */

fun <T> Result<T>.getException(): Throwable {
    return exceptionOrNull() ?: Throwable(ResponseConstant.SOMETHING_GOES_WRONG_REPO)
}

fun <R, E: Any> Result<R>.toBaseResponseBuilder(): BaseResponseBuilder<R, E> {
    return BaseResponseBuilder(this)
}

class BaseResponseBuilder<R, E: Any>(private val result: Result<R>) {
    private var entity: E? = null

    fun setEntity(converter: (R?) -> E): BaseResponseBuilder<R, E> {
        entity = converter.invoke(result.getOrNull())
        return this
    }

    suspend fun addOnSuccess(callback: (E?) -> Unit): BaseResponseBuilder<R, E> {
        result.onSuccess { callback(entity) }
        return this
    }

    fun addOnFailure(callback: () -> Unit): BaseResponseBuilder<R, E> {
        result.onFailure { callback() }
        return this
    }

    fun build(): BaseResponse<E> {
        return BaseResponse(entity, result.exceptionOrNull())
    }

}