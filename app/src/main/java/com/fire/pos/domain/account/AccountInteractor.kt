package com.fire.pos.domain.account

import com.fire.core.model.Result


/**
 * Created by Chandra.
 **/

interface AccountInteractor {

    suspend fun logout(): Result<Boolean>

}