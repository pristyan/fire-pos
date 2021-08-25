package com.fire.pos.data.repository.account

import com.fire.pos.model.entity.UserEntity
import com.fire.pos.model.response.BaseResponse


/**
 * Created by Chandra.
 **/

interface AccountRepository {

    suspend fun isLoggedIn(): BaseResponse<Boolean>

    suspend fun loginWithEmailPassword(email: String, password: String): BaseResponse<UserEntity>

    suspend fun registerWithEmailPassword(
        email: String,
        password: String,
        storeName: String
    ): BaseResponse<UserEntity>

    suspend fun logout()

}