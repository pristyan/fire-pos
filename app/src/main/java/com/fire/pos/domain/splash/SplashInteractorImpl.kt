package com.fire.pos.domain.splash

import com.fire.pos.data.repository.account.AccountRepository
import com.fire.core.model.Result
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class SplashInteractorImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : SplashInteractor {

    override suspend fun isLoggedIn(): Result<Boolean> {
        return accountRepository.isLoggedIn()
    }
}