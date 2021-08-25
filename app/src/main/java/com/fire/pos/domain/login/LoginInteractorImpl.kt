package com.fire.pos.domain.login

import com.fire.pos.data.repository.account.AccountRepository
import com.fire.pos.model.entity.UserEntity
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.User
import com.fire.pos.util.toResultBuilder
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class LoginInteractorImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : LoginInteractor {

    override suspend fun isLoggedIn(): Boolean {
        return accountRepository.isLoggedIn().data ?: false
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return accountRepository.loginWithEmailPassword(email, password)
            .toResultBuilder<UserEntity, User>()
            .setData { User(it) }
            .build()
    }

}