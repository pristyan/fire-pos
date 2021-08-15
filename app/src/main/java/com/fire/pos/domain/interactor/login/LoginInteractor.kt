package com.fire.pos.domain.interactor.login

import com.fire.pos.data.response.Result
import com.fire.pos.data.view.User


/**
 * Created by Chandra.
 **/

interface LoginInteractor {

    suspend fun isLoggedIn(): Result<Boolean>

    suspend fun login(email: String, password: String): Result<User>

}