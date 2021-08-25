package com.fire.pos.domain.registration

import com.fire.pos.data.repository.account.AccountRepository
import com.fire.pos.model.entity.UserEntity
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.User
import com.fire.pos.util.toResultBuilder
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class RegistrationInteractorImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : RegistrationInteractor {

    override suspend fun registerUser(
        email: String,
        password: String,
        storeName: String
    ): Result<User> {
        return accountRepository.registerWithEmailPassword(email, password, storeName)
            .toResultBuilder<UserEntity, User>()
            .setData { User(it) }
            .build()
    }
}