package com.fire.pos.domain.account

import com.fire.pos.data.repository.account.AccountRepository
import com.fire.pos.data.repository.cart.CartRepository
import com.fire.pos.model.response.Result
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountInteractorImpl @Inject constructor(
    private val accountRepository: AccountRepository,
    private val cartRepository: CartRepository
): AccountInteractor {

    override suspend fun logout(): Result<Boolean> {
        return when (val cResult = cartRepository.clearCart()) {
            is Result.Error -> Result.Error(cResult.message)
            is Result.Success -> {
                accountRepository.logout()
                Result.Success(true)
            }
        }
    }

}