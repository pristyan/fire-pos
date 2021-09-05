package com.fire.pos.domain.login

import com.fire.pos.data.repository.account.AccountRepository
import com.fire.core.model.Result
import com.fire.pos.model.view.User
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class LoginInteractorImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : LoginInteractor {

    override suspend fun isLoggedIn(): Boolean {
        return when (val result = accountRepository.isLoggedIn()) {
            is Result.Error -> false
            is Result.Success -> result.data ?: false
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return when (val result = accountRepository.loginWithEmailPassword(email, password)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(User(result.data))
        }
    }

}