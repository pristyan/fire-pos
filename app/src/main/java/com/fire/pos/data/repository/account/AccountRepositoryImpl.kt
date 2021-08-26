package com.fire.pos.data.repository.account

import com.fire.pos.data.source.local.account.AccountLocalDataSource
import com.fire.pos.data.source.remote.account.AccountRemoteDataSource
import com.fire.pos.model.entity.UserEntity
import com.fire.pos.model.response.BaseResponse
import com.fire.pos.util.getException
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountRepositoryImpl @Inject constructor(
    private val accountLocalDataSource: AccountLocalDataSource,
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    override suspend fun isLoggedIn(): BaseResponse<Boolean> {
        return BaseResponse(accountLocalDataSource.isLoggedIn())
    }

    override suspend fun loginWithEmailPassword(
        email: String,
        password: String
    ): BaseResponse<UserEntity> {
        val result = accountRemoteDataSource.loginWithEmailPassword(email, password)
        return if (result.isSuccess) {
            val data = UserEntity(result.getOrNull())
            accountLocalDataSource.setUser(data)
            BaseResponse(data)
        } else {
            BaseResponse(result.getException())
        }
    }

    override suspend fun registerWithEmailPassword(
        email: String,
        password: String,
        storeName: String
    ): BaseResponse<UserEntity> {
        val userResult = accountRemoteDataSource.registerWithEmailPassword(email, password)
        val user = userResult.getOrNull()?.user
        return if (userResult.isSuccess && user != null) {
            val storeResult = accountRemoteDataSource.registerToFirestore(user.uid, storeName)
            if (storeResult.isSuccess) {
                val userEntity = UserEntity(user.uid, user.email)
                accountLocalDataSource.setUser(userEntity)
                BaseResponse(UserEntity(user.uid, user.email))
            } else {
                BaseResponse(storeResult.getException())
            }
        } else {
            BaseResponse(userResult.getException())
        }
    }

    override suspend fun logout() {
        accountRemoteDataSource.logout()
        accountLocalDataSource.deleteUser()
    }


}