package com.fire.pos.domain.interactor.login

import com.fire.pos.data.constant.ResponseConstant
import com.fire.pos.data.response.Result
import com.fire.pos.data.view.User
import com.fire.pos.domain.repository.remote.AccountRemoteDataSource
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class LoginInteractorImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : LoginInteractor {

    override suspend fun isLoggedIn(): Result<Boolean> {
        val response = accountRemoteDataSource.getCurrentUser()
        return if (response.isSuccess) {
            Result.Success(true)
        } else {
            Result.Error(response.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG)
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        val response = accountRemoteDataSource.loginWithEmailPassword(email, password)
        return if (response.isSuccess) {
            Result.Success(User(response.data?.user))
        } else {
            Result.Error(response.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG)
        }
    }

}