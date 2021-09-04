package com.fire.pos.domain.splash

import com.fire.pos.model.response.Result


/**
 * Created by Chandra.
 **/

interface SplashInteractor {

    suspend fun isLoggedIn(): Result<Boolean>

}