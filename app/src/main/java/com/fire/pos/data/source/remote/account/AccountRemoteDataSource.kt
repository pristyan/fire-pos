package com.fire.pos.data.source.remote.account

import com.fire.pos.model.response.BaseResponse
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference


/**
 * Created by Chandra.
 **/

interface AccountRemoteDataSource {

    suspend fun getCurrentUser(): BaseResponse<FirebaseUser>

    suspend fun loginWithEmailPassword(email: String, password: String): Result<AuthResult>

    suspend fun registerWithEmailPassword(email: String, password: String): Result<AuthResult>

    suspend fun registerToFirestore(uid: String, storeName: String): Result<DocumentReference>

    suspend fun logout()
}