package com.fire.pos.domain.repository.remote

import com.fire.pos.data.response.BaseResponse
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference


/**
 * Created by Chandra.
 **/

interface AccountRemoteDataSource {

    suspend fun getCurrentUser(): BaseResponse<FirebaseUser>

    suspend fun loginWithEmailPassword(email: String, password: String): BaseResponse<AuthResult>

    suspend fun registerWithEmailPassword(email: String, password: String): BaseResponse<AuthResult>

    suspend fun registerToFirestore(uid: String, storeName: String): BaseResponse<DocumentReference>

    suspend fun logout()
}