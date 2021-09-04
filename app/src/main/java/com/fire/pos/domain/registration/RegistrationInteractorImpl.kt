package com.fire.pos.domain.registration

import com.fire.pos.data.repository.account.AccountRepository
import com.fire.pos.model.entity.StoreEntity
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.User
import com.fire.pos.util.FirestoreIdGenerator
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
        val store = StoreEntity(
            id = FirestoreIdGenerator.generateStoreId(),
            name = storeName
        )
        return when (
            val response = accountRepository.registerWithEmailPassword(email, password, store)
        ) {
            is Result.Error -> Result.Error(response.message)
            is Result.Success -> Result.Success(User(response.data))
        }
    }
}