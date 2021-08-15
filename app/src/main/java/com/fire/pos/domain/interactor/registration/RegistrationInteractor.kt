package com.fire.pos.domain.interactor.registration

import com.fire.pos.data.response.Result
import com.fire.pos.data.view.User


/**
 * Created by Chandra.
 **/

interface RegistrationInteractor {

    suspend fun registerUser(email: String, password: String, storeName: String): Result<User>

}