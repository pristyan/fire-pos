package com.fire.pos.data.source.remote.account

import com.fire.pos.model.entity.StoreEntity
import com.fire.core.model.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser


/**
 * Created by Chandra.
 **/

interface AccountRemoteDataSource {

    suspend fun getCurrentUser(): Result<FirebaseUser>

    suspend fun loginWithEmailPassword(email: String, password: String): Result<AuthResult>

    suspend fun registerWithEmailPassword(email: String, password: String): Result<AuthResult>

    suspend fun registerToFirestore(uid: String, storeEntity: StoreEntity): Result<Void>

    suspend fun logout()
}