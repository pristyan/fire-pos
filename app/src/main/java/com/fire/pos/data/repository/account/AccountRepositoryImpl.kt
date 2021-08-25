package com.fire.pos.data.repository.account

import com.fire.pos.data.source.local.account.AccountLocalDataSource
import com.fire.pos.data.source.remote.account.AccountRemoteDataSource
import com.fire.pos.model.entity.UserEntity
import com.fire.pos.model.response.BaseResponse
import com.fire.pos.util.getException
import com.fire.pos.util.toBaseResponseBuilder
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
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
        // login to firebase auth
        return accountRemoteDataSource.loginWithEmailPassword(email, password)
            .toBaseResponseBuilder<AuthResult, UserEntity>()
            .setEntity { UserEntity(it) }
            .addOnSuccess {
                // save to preference
                it?.let { user -> accountLocalDataSource.setUser(user) }
            }
            .build()
    }

    override suspend fun registerWithEmailPassword(
        email: String,
        password: String,
        storeName: String
    ): BaseResponse<UserEntity> {
        // register to firebase auth
        val userResult = accountRemoteDataSource.registerWithEmailPassword(email, password)
        val user = userResult.getOrNull()?.user
        return if (userResult.isSuccess && user != null) {

            // Register to FireStore
            val storeResult = accountRemoteDataSource.registerToFirestore(user.uid, storeName)
            storeResult.toBaseResponseBuilder<DocumentReference, UserEntity>()
                .setEntity { UserEntity(user.uid, user.email) }
                .addOnSuccess {
                    // save to preference
                    it?.let { user -> accountLocalDataSource.setUser(user) }
                }
                .build()

        } else {
            BaseResponse(throwable = userResult.getException())
        }
    }

    override suspend fun logout() {
        accountRemoteDataSource.logout()
        accountLocalDataSource.deleteUser()
    }


}