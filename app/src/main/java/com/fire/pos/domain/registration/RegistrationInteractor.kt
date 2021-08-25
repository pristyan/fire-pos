package com.fire.pos.domain.registration

import com.fire.pos.model.response.Result
import com.fire.pos.model.view.User


/**
 * Created by Chandra.
 **/

interface RegistrationInteractor {

    suspend fun registerUser(email: String, password: String, storeName: String): Result<User>

}