package com.fire.pos.domain.login

import com.fire.core.model.Result
import com.fire.pos.model.view.User


/**
 * Created by Chandra.
 **/

interface LoginInteractor {

    suspend fun isLoggedIn(): Boolean

    suspend fun login(email: String, password: String): Result<User>

}