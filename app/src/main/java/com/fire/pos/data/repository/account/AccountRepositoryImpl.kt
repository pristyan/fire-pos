package com.fire.pos.data.repository.account

import com.fire.pos.data.source.local.account.AccountLocalDataSource
import com.fire.pos.data.source.remote.account.AccountRemoteDataSource
import com.fire.pos.model.entity.UserEntity
import com.fire.pos.model.response.Result
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountRepositoryImpl @Inject constructor(
    private val accountLocalDataSource: AccountLocalDataSource,
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    override suspend fun isLoggedIn(): Result<Boolean> {
        return Result.Success(accountLocalDataSource.isLoggedIn())
    }

    override suspend fun loginWithEmailPassword(
        email: String,
        password: String
    ): Result<UserEntity> {
        return when (val result = accountRemoteDataSource.loginWithEmailPassword(email, password)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val data = UserEntity(result.data)
                accountLocalDataSource.setUser(data)
                Result.Success(data)
            }
        }
    }

    override suspend fun registerWithEmailPassword(
        email: String,
        password: String,
        storeName: String
    ): Result<UserEntity> {
        return when (
            val userResult = accountRemoteDataSource.registerWithEmailPassword(email, password)
        ) {
            is Result.Error -> Result.Error(userResult.message)
            is Result.Success -> {
                val user = userResult.data?.user
                if (user != null) {
                    when (
                        val storeResult = accountRemoteDataSource.registerToFirestore(
                            user.uid, storeName
                        )
                    ) {
                        is Result.Error -> Result.Error(storeResult.message)
                        is Result.Success -> {
                            val userEntity = UserEntity(user.uid, user.email)
                            accountLocalDataSource.setUser(userEntity)
                            Result.Success(UserEntity(user.uid, user.email))
                        }
                    }
                } else {
                    Result.Error("User not found")
                }
            }
        }
    }

    override suspend fun logout() {
        accountRemoteDataSource.logout()
        accountLocalDataSource.deleteUser()
    }


}