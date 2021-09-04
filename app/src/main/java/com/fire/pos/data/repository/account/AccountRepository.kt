package com.fire.pos.data.repository.account

import com.fire.pos.model.entity.StoreEntity
import com.fire.pos.model.entity.UserEntity
import com.fire.pos.model.response.Result


/**
 * Created by Chandra.
 **/

interface AccountRepository {

    suspend fun isLoggedIn(): Result<Boolean>

    suspend fun loginWithEmailPassword(email: String, password: String): Result<UserEntity>

    suspend fun registerWithEmailPassword(
        email: String,
        password: String,
        storeEntity: StoreEntity
    ): Result<UserEntity>

    suspend fun logout()

}