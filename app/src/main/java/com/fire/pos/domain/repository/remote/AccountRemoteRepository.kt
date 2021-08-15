package com.fire.pos.domain.repository.remote

import com.fire.pos.data.response.BaseResponse
import com.fire.pos.data.constant.FirestoreConstant
import com.fire.pos.util.await
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountRemoteRepository @Inject constructor(
    private var firebaseAuth: FirebaseAuth,
    private var firebaseFirestore: FirebaseFirestore
) : AccountRemoteDataSource {

    override suspend fun getCurrentUser(): BaseResponse<FirebaseUser> {
        val user = firebaseAuth.currentUser
        return if (user == null) {
            BaseResponse(data = null, throwable = Exception("User not logged in yet"))
        } else {
            BaseResponse(data = user, throwable = null)
        }
    }

    override suspend fun loginWithEmailPassword(
        email: String,
        password: String
    ): BaseResponse<AuthResult> {
        val task = firebaseAuth.signInWithEmailAndPassword(email, password)
        return task.await()
    }

    override suspend fun registerWithEmailPassword(
        email: String,
        password: String
    ): BaseResponse<AuthResult> {
        val task = firebaseAuth.createUserWithEmailAndPassword(email, password)
        return task.await()
    }

    override suspend fun registerToFirestore(
        uid: String,
        storeName: String
    ): BaseResponse<DocumentReference> {
        val data = mapOf(
            FirestoreConstant.FIELD_NAME to storeName,
            FirestoreConstant.FIELD_ADDRESS to "",
            FirestoreConstant.FIELD_PHONE to ""
        )
        val task = firebaseFirestore
            .collection(FirestoreConstant.COLLECTION_USERS)
            .document(uid)
            .collection(FirestoreConstant.COLLECTION_STORES)
            .add(data)

        return task.await()
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}