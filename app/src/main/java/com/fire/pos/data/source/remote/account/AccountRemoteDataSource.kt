package com.fire.pos.data.source.remote.account

import com.fire.pos.model.entity.StoreEntity
import com.fire.pos.model.response.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference


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