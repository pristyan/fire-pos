package com.fire.pos.domain.splash

import com.fire.core.model.Result


/**
 * Created by Chandra.
 **/

interface SplashInteractor {

    suspend fun isLoggedIn(): Result<Boolean>

}