package com.fire.pos.domain.account

import com.fire.pos.model.response.Result


/**
 * Created by Chandra.
 **/

interface AccountInteractor {

    suspend fun logout(): Result<Boolean>

}