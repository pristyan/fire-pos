package com.fire.pos.domain.registration

import com.fire.core.model.Result
import com.fire.pos.model.view.User


/**
 * Created by Chandra.
 **/

interface RegistrationInteractor {

    suspend fun registerUser(email: String, password: String, storeName: String): Result<User>

}