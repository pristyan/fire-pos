package com.fire.pos.domain.interactor.registration

import com.fire.pos.data.constant.ResponseConstant
import com.fire.pos.data.response.Result
import com.fire.pos.data.view.User
import com.fire.pos.domain.repository.remote.AccountRemoteDataSource
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class RegistrationInteractorImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : RegistrationInteractor {

    override suspend fun registerUser(
        email: String,
        password: String,
        storeName: String
    ): Result<User> {

        // register to firebase auth
        val authResponse = accountRemoteDataSource.registerWithEmailPassword(email, password)
        if (authResponse.isSuccess && authResponse.data?.user != null) {

            // register to firebase store
            val dbResponse = accountRemoteDataSource.registerToFirestore(
                uid = authResponse.data.user?.uid as String,
                storeName = storeName
            )

            return if (dbResponse.isSuccess) {
                Result.Success(User(authResponse.data.user))
            } else {
                Result.Error(dbResponse.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG)
            }
        } else {
            return Result.Error(
                authResponse.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG
            )
        }
    }


}